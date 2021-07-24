package com.fitlers.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunSummary;
import com.fitlers.model.request.AddRunDetailsRequest;
import com.fitlers.model.request.AggregateBy;
import com.fitlers.model.request.BucketByTime;
import com.fitlers.model.request.GetRunsForUserRequest;
import com.fitlers.model.request.GetUserGoogleFitDataRequest;
import com.fitlers.model.request.GoogleFitRestRequest;
import com.fitlers.model.response.GetRunsForUserResponse;
import com.fitlers.model.response.GetUserGoogleFitDataResponse;
import com.fitlers.proxy.ServiceProxy;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;

@Component
public class GetGoogleFitDataRequestProcessor extends RequestProcessor<GoogleFitRestRequest, GetUserGoogleFitDataResponse> {

	
	@Autowired
	ServiceProxy serviceProxy;
	
	@Autowired
	RunDetailsRepository runDetailsRepository;

	@Autowired
	RunSummaryRepository runSummaryRepository;

	public static final Logger logger = LoggerFactory.getLogger(GetGoogleFitDataRequestProcessor.class);

	@Override
	public boolean isRequestValid(GoogleFitRestRequest request) throws Exception {
		if (StringUtils.isEmpty(request.getUserId()==null)) {
			throw new Exception("AggregateBy is not populated");
		}
		return super.isRequestValid(request);
	}

	
	@Override
	public GetUserGoogleFitDataResponse doProcessing(GoogleFitRestRequest googleFitRestRequest) throws Exception {
		logger.info("GetGoogleFitDataRequestProcessor doProcessing Started for User Id " + googleFitRestRequest.getUserId());
		GetUserGoogleFitDataResponse response = new GetUserGoogleFitDataResponse();
		GetUserGoogleFitDataRequest getUserGoogleFitDataRequest = new GetUserGoogleFitDataRequest();
		AggregateBy aggregateBy[] = populateAggregateData();
		getUserGoogleFitDataRequest.setAggregateBy(aggregateBy);
		BucketByTime bucketByTime = new BucketByTime();
		bucketByTime.setDurationMillis(720000000);
		getUserGoogleFitDataRequest.setBucketByTime(bucketByTime);
		getUserGoogleFitDataRequest.setStartTimeMillis(1625941800000L);
		getUserGoogleFitDataRequest.setEndTimeMillis(1626028199000L);
		 response = serviceProxy.getGoogleFitData(getUserGoogleFitDataRequest);
		 
		 AddRunDetailsRequest addRunDetailsRequest = new AddRunDetailsRequest();
		 List<RunDetails> runDetailsList = new ArrayList();
		 RunDetails runDetails= new RunDetails();
		 runDetails.setRunId(1L);
		 runDetails.setRunDistance((double)response.getBucket()[0].getDataset()[1].getPoint()[0].getValue()[0].getFpVal());
		 runDetails.setRunPace((double)response.getBucket()[0].getDataset()[3].getPoint()[0].getValue()[0].getFpVal());
		 runDetails.setRunCaloriesBurnt((double)response.getBucket()[0].getDataset()[2].getPoint()[0].getValue()[0].getFpVal());
		 runDetails.setUserId(googleFitRestRequest.getUserId());
		 runDetailsList.add(runDetails);
		 addRunDetailsRequest.setUserId(googleFitRestRequest.getUserId());
		 addRunDetailsRequest.setRunDetailsList(runDetailsList);
		 addRuns(addRunDetailsRequest);
		 addOrUpdateRunSummary(addRunDetailsRequest);
		logger.info("GetGoogleFitDataRequestProcessor doProcessing Response Successfully sent");
		return response;
		
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
		runSummaryNew.setTotalCredits(0.0);
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
		existingRunSummary.setTotalCredits(0.0);
		runSummaryRepository.save(existingRunSummary);
	}

	private AggregateBy[] populateAggregateData() {
		List<AggregateBy> aggregateByList = new ArrayList<AggregateBy>();
		populateAggregateByInternal(aggregateByList,"com.google.step_count.delta","derived:com.google.step_count.delta:com.google.android.gms:estimated_steps");
		populateAggregateByInternal(aggregateByList,"com.google.distance.delta","derived:com.google.distance.delta:com.google.android.gms:merge_distance_delta");
		populateAggregateByInternal(aggregateByList,"com.google.calories.expended","derived:com.google.calories.expended:com.google.android.gms:merge_calories_expended");
		populateAggregateByInternal(aggregateByList,"ccom.google.speed.summary","derived:com.google.speed:com.google.android.gms:merge_speed");
		populateAggregateByInternal(aggregateByList,"com.google.active_minutes","derived:com.google.active_minutes:com.google.android.gms:merge_active_minutes");
		populateAggregateByInternal(aggregateByList,"com.google.activity.segment","derived:com.google.activity.segment:com.google.android.gms:merge_activity_segments");
		return aggregateByList.toArray(new AggregateBy[aggregateByList.size()]);
	}

	private void populateAggregateByInternal(List<AggregateBy> aggregateByList, String dataTypeName, String dataSourceId) {
		AggregateBy aggregateBy = new AggregateBy();
		aggregateBy.setDataTypeName(dataTypeName);
		aggregateBy.setDataSourceId(dataSourceId);
		aggregateByList.add(aggregateBy);
	}

	

	/*
	 * private void populateEventDetailsIfApplicable(List<RunDetails>
	 * runDetailsList) { Map<Long, EventDetails> mapOfEventDetails = new
	 * HashMap<Long, EventDetails>(); Map<EventResultDetailsId, EventResultDetails>
	 * mapOfEventResultDetails = new HashMap<EventResultDetailsId,
	 * EventResultDetails>(); runDetailsList.parallelStream().filter(run ->
	 * !Objects.isNull(run.getEventId())).forEach(eventRun -> {
	 * mapOfEventDetails.putIfAbsent(eventRun.getEventId(), null);
	 * mapOfEventResultDetails.putIfAbsent(new
	 * EventResultDetailsId(eventRun.getUserId(), eventRun.getEventId()), null); });
	 * 
	 * List<EventDetails> listOfEventDetails =
	 * eventDetailsRepo.findAllById(mapOfEventDetails.keySet());
	 * List<EventResultDetails> listOfEventResultDetails = eventResultDetailsRepo
	 * .findAllById(mapOfEventResultDetails.keySet());
	 * 
	 * listOfEventDetails.parallelStream().forEach(eventDetail -> {
	 * mapOfEventDetails.put(eventDetail.getEventId(), eventDetail); });
	 * 
	 * listOfEventResultDetails.parallelStream().forEach(eventResultDetail -> {
	 * mapOfEventResultDetails.put( new
	 * EventResultDetailsId(eventResultDetail.getUserId(),
	 * eventResultDetail.getEventId()), eventResultDetail); });
	 * 
	 * runDetailsList.parallelStream().filter(run ->
	 * !Objects.isNull(run.getEventId())).forEach(eventRun -> {
	 * eventRun.setEventDetails(mapOfEventDetails.get(eventRun.getEventId()));
	 * eventRun.setEventResultDetails( mapOfEventResultDetails.get(new
	 * EventResultDetailsId(eventRun.getUserId(), eventRun.getEventId()))); }); }
	 */

}
