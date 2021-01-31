package com.onehealth.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.onehealth.core.exceptions.NoUserFoundException;
import com.onehealth.core.kafka.KafkaUtils;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.RunDetailsId;
import com.onehealth.entities.RunSummary;
import com.onehealth.model.request.AddRunDetailsRequest;
import com.onehealth.repo.RunDetailsRepository;
import com.onehealth.repo.RunSummaryRepository;

@Component
public class AddRunsRequestProcessor extends RequestProcessor<AddRunDetailsRequest, Boolean> {

	@Autowired
	RunDetailsRepository runDetailsRepository;

	@Autowired
	RunSummaryRepository runSummaryRepository;

	@Autowired
	KafkaUtils kafkaUtils;

	private static final Logger LOG = Logger.getLogger(AddRunsRequestProcessor.class);

	@Override
	public boolean isRequestValid(AddRunDetailsRequest request) {
		if (StringUtils.isEmpty(request.getUserId())) {
			LOG.error("Invalid user id found, unable to process request");
			throw new NoUserFoundException();
		}
		LOG.debug("Valid add run details request, processing..");
		return super.isRequestValid(request);
	}

	@Override
	public void preProcess(AddRunDetailsRequest request) {
		List<RunDetailsId> listOfIds = new ArrayList<RunDetailsId>();
		request.getRunDetailsList().stream().forEach(run -> {
			listOfIds.add(new RunDetailsId(run.getRunId(), request.getUserId()));
		});
		request.getRunDetailsList().removeAll(runDetailsRepository.findAllById(listOfIds));
		super.preProcess(request);
	}

	@Override
	public Boolean doProcessing(AddRunDetailsRequest request) {
		boolean status = addRunsAndUpdateSummary(request);
		updateKafkaIfNeeded(request);
		return status;
	}

	@Transactional
	private boolean addRunsAndUpdateSummary(AddRunDetailsRequest request) {
		addRuns(request);
		addOrUpdateRunSummary(request);
		return true;
	}

	private void addRuns(AddRunDetailsRequest request) {
		LOG.info("Saving run details for "+request.getRunDetailsList().size()+" user(s)");
		runDetailsRepository.saveAll(request.getRunDetailsList());
		LOG.info("Run details saved successfuly.");
	}

	private void addOrUpdateRunSummary(AddRunDetailsRequest request) {
		RunSummary runSummary = new RunSummary();
		runSummary.setUserId(request.getUserId());
		Example<RunSummary> runSummaryQueryObj = Example.of(runSummary);
		Optional<RunSummary> existingRunSummaryOptional = runSummaryRepository.findOne(runSummaryQueryObj);
		if (existingRunSummaryOptional.isPresent()) {
			updateRunSummary(request, existingRunSummaryOptional);
		}
		else {
			LOG.debug("No existing run summary found for user with id"+request.getUserId()+", creating new run summary.");
			addRunSummary(request);
		}
	}

	private void addRunSummary(AddRunDetailsRequest request) {
		RunSummary runSummaryNew = new RunSummary();
		runSummaryNew.setUserId(request.getUserId());
		LOG.debug("Run summaries to be created : "+request.getRunDetailsList().size());
		runSummaryNew.setTotalRuns(request.getRunDetailsList().size());
		runSummaryNew.setTotalDistance(
				(request.getRunDetailsList().stream().mapToDouble(run -> run.getRunDistance()).sum()));
		runSummaryNew.setAverageDistance(runSummaryNew.getTotalDistance() / runSummaryNew.getTotalRuns());
		double avgPace = (request.getRunDetailsList().stream().mapToDouble(run -> run.getRunPace()).sum())
				/ runSummaryNew.getTotalRuns();
		runSummaryNew.setAveragePace(avgPace);
		runSummaryRepository.save(runSummaryNew);
		LOG.debug("Run summaries created : "+runSummaryNew.getTotalRuns());
	}
 
	private void updateRunSummary(AddRunDetailsRequest request, Optional<RunSummary> existingRunSummaryOptional) {
		RunSummary existingRunSummary = existingRunSummaryOptional.get();
		LOG.debug("Updating existing Run summary for user :"+existingRunSummary.getUserDetails().getUserFirstName()+
				" "+existingRunSummary.getUserDetails().getUserLastName()+" with id :"+existingRunSummary.getUserId());
		
		existingRunSummary.setTotalDistance(existingRunSummary.getTotalDistance()
				+ request.getRunDetailsList().stream().mapToDouble(run -> run.getRunDistance()).sum());
		double existingAvgPace = existingRunSummary.getAveragePace();
		double totalSumOfAvgPaceForNewRuns = request.getRunDetailsList().stream().mapToDouble(run -> run.getRunPace())
				.sum();
		double updatedAvgPace = ((existingAvgPace * (existingRunSummary.getTotalRuns())) + totalSumOfAvgPaceForNewRuns)
				/ (existingRunSummary.getTotalRuns() + request.getRunDetailsList().size());
		existingRunSummary.setAveragePace(updatedAvgPace);
		existingRunSummary.setTotalRuns(existingRunSummary.getTotalRuns() + request.getRunDetailsList().size());
		LOG.debug("Run summaries to be updated : "+existingRunSummary.getTotalRuns());
		existingRunSummary
				.setAverageDistance(existingRunSummary.getTotalDistance() / existingRunSummary.getTotalRuns());
		runSummaryRepository.save(existingRunSummary);
		LOG.debug("Updated Run summary for user :"+existingRunSummary.getUserDetails().getUserFirstName()+
				" "+existingRunSummary.getUserDetails().getUserLastName());

	}

	private void updateKafkaIfNeeded(AddRunDetailsRequest request) {
		request.getRunDetailsList().parallelStream().filter(run -> run.getEventId() > 0).forEach(r -> {
			String topicName = "EVENT_RUN_SUBMISSION_" + r.getEventId();
			kafkaUtils.sendMessage(topicName, r);
		});
	}

}
