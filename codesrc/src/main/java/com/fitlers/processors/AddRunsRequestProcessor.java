package com.fitlers.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fitlers.core.kafka.KafkaUtils;
import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.EventRegistrationDetails;
import com.fitlers.entities.EventRegistrationDetailsId;
import com.fitlers.entities.RunDetailsId;
import com.fitlers.entities.RunSummary;
import com.fitlers.model.request.AddRunDetailsRequest;
import com.fitlers.repo.EventRegistrationDetailsRepository;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;

@Component
public class AddRunsRequestProcessor extends RequestProcessor<AddRunDetailsRequest, Boolean> {

	@Autowired
	RunDetailsRepository runDetailsRepository;

	@Autowired
	RunSummaryRepository runSummaryRepository;
	
	@Autowired
	EventRegistrationDetailsRepository eventRegistrationDetailsRepository;

	@Autowired(required = false)
	KafkaUtils kafkaUtils;
	
	@Autowired
	Environment env;
	
	public static final Logger logger=LoggerFactory.getLogger(AddRunsRequestProcessor.class);

	@Override
	public boolean isRequestValid(AddRunDetailsRequest request) throws Exception {
		if (StringUtils.isEmpty(request.getUserId())) {
			throw new Exception("User Id is not populated");
		}
		else if(CollectionUtils.isEmpty(request.getRunDetailsList())) {
			throw new Exception("Run Details List Passed in the input is empty");
		}
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
	public Boolean doProcessing(AddRunDetailsRequest request) throws Exception {
		logger.info("AddRunsRequestProcessor doProcessing Started for User Id "+request.getUserId());
		boolean status = addRunsAndUpdateSummary(request);
		updateKafkaIfNeeded(request);
		logger.info("AddRunsRequestProcessor doProcessing Completed for User Id "+request.getUserId());
		return status;
	}

	@Transactional
	private boolean addRunsAndUpdateSummary(AddRunDetailsRequest request) {
		addRuns(request);
		addOrUpdateRunSummary(request);
		updateEventRegistrationDetailsIfRequired(request);
		return true;
	}

	private void addRuns(AddRunDetailsRequest request) {
		runDetailsRepository.saveAll(request.getRunDetailsList());
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
			addRunSummary(request);
		}
	}
	
	private void updateEventRegistrationDetailsIfRequired(AddRunDetailsRequest request) {
		List<EventRegistrationDetailsId> listOfIds = new ArrayList<EventRegistrationDetailsId>();
		Map<Long, Long> mapOfEventsAndRuns = new HashMap<Long, Long>();
		request.getRunDetailsList().parallelStream().filter(run -> run.getEventId() > 0).forEach(r -> {
			listOfIds.add(new EventRegistrationDetailsId(r.getEventId(), request.getUserId()));
			mapOfEventsAndRuns.put(r.getEventId(), r.getRunId());
		});
		List<EventRegistrationDetails> listOfEventRegistrationDetails = eventRegistrationDetailsRepository
				.findAllById(listOfIds);
		listOfEventRegistrationDetails.parallelStream().forEach(eventRegistration -> {
			eventRegistration.setRunId(mapOfEventsAndRuns.getOrDefault(eventRegistration.getEventId(), (long) 0));
		});
		eventRegistrationDetailsRepository.saveAll(listOfEventRegistrationDetails);
	}

	private void addRunSummary(AddRunDetailsRequest request) {
		RunSummary runSummaryNew = new RunSummary();
		runSummaryNew.setUserId(request.getUserId());
		runSummaryNew.setTotalRuns(request.getRunDetailsList().size());
		runSummaryNew.setTotalDistance(
				(request.getRunDetailsList().stream().mapToDouble(run -> run.getRunDistance()).sum()));
		runSummaryNew.setAverageDistance(runSummaryNew.getTotalDistance() / runSummaryNew.getTotalRuns());
		
		double avgPace = (request.getRunDetailsList().stream().mapToDouble(run -> run.getRunPace()).sum())
				/ runSummaryNew.getTotalRuns();
		runSummaryNew.setAveragePace(avgPace);
		
		double avgCaloriesBurnt = (request.getRunDetailsList().stream().mapToDouble(run -> run.getRunCaloriesBurnt()).sum())
				/ runSummaryNew.getTotalRuns();
		runSummaryNew.setAverageCaloriesBurnt(avgCaloriesBurnt);
		runSummaryRepository.save(runSummaryNew);
	}

	private void updateRunSummary(AddRunDetailsRequest request, Optional<RunSummary> existingRunSummaryOptional) {
		RunSummary existingRunSummary = existingRunSummaryOptional.get();
		existingRunSummary.setTotalDistance(existingRunSummary.getTotalDistance()
				+ request.getRunDetailsList().stream().mapToDouble(run -> run.getRunDistance()).sum());
		
		double existingAvgPace = existingRunSummary.getAveragePace();
		double totalSumOfAvgPaceForNewRuns = request.getRunDetailsList().stream().mapToDouble(run -> run.getRunPace())
				.sum();
		double updatedAvgPace = ((existingAvgPace * (existingRunSummary.getTotalRuns())) + totalSumOfAvgPaceForNewRuns)
				/ (existingRunSummary.getTotalRuns() + request.getRunDetailsList().size());
		existingRunSummary.setAveragePace(updatedAvgPace);
		
		double existingAvgCaloriesBurnt = existingRunSummary.getAverageCaloriesBurnt();
		double totalSumOfAvgCaloriesBurntForNewRuns = request.getRunDetailsList().stream().mapToDouble(run -> run.getRunCaloriesBurnt())
				.sum();
		double updatedAvgCaloriesBurnt = ((existingAvgCaloriesBurnt * (existingRunSummary.getTotalRuns())) + totalSumOfAvgCaloriesBurntForNewRuns)
				/ (existingRunSummary.getTotalRuns() + request.getRunDetailsList().size());
		existingRunSummary.setAverageCaloriesBurnt(updatedAvgCaloriesBurnt);
		
		existingRunSummary.setTotalRuns(existingRunSummary.getTotalRuns() + request.getRunDetailsList().size());
		existingRunSummary
				.setAverageDistance(existingRunSummary.getTotalDistance() / existingRunSummary.getTotalRuns());
		runSummaryRepository.save(existingRunSummary);
	}

	private void updateKafkaIfNeeded(AddRunDetailsRequest request) {
		if (env.acceptsProfiles(Profiles.of("prod"))) {
			request.getRunDetailsList().parallelStream().filter(run -> run.getEventId() > 0).forEach(r -> {
				String topicName = "EVENT_RUN_SUBMISSION_" + r.getEventId();
				kafkaUtils.sendMessage(topicName, r);
				logger.info("AddRunsRequestProcessor Update Kafka Completed for User Id " + request.getUserId()
						+ " and event id " + r.getEventId());
			});
		}
	}

}
