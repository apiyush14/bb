package com.fitlers.processors;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.RunDetails;
import com.fitlers.model.request.GetRunsForUserRequest;
import com.fitlers.model.response.GetRunsForUserResponse;
import com.fitlers.repo.RunDetailsRepository;

@Component
public class GetRunsForUserRequestProcessor extends RequestProcessor<GetRunsForUserRequest, GetRunsForUserResponse> {

	@Autowired
	private RunDetailsRepository runDetailsRepo;
	/*
	 * @Autowired private EventDetailsRepository eventDetailsRepo;
	 * 
	 * @Autowired private EventResultDetailsRepository eventResultDetailsRepo;
	 */

	public static final Logger logger = LoggerFactory.getLogger(GetRunsForUserRequestProcessor.class);

	@Override
	public boolean isRequestValid(GetRunsForUserRequest request) throws Exception {
		if (StringUtils.isEmpty(request.getUserId())) {
			throw new Exception("User Id is not populated");
		}
		return super.isRequestValid(request);
	}

	@Override
	public GetRunsForUserResponse doProcessing(GetRunsForUserRequest request) throws Exception {
		logger.info("GetRunsForUserRequestProcessor doProcessing Started for User Id " + request.getUserId());
		GetRunsForUserResponse response = new GetRunsForUserResponse();
		RunDetails runDetailsQueryObj = new RunDetails();
		runDetailsQueryObj.setUserId(request.getUserId());

		Example<RunDetails> runDetailsQueryExample = Example.of(runDetailsQueryObj);
		List<RunDetails> runDetailsList;

		if (Objects.isNull(request.getPageNumber())) {
			if (request.isOnlyEventRunsRequired()) {
				runDetailsList = runDetailsRepo.findAllEventRuns(Sort.by("runId"));
			} else {
				runDetailsList = runDetailsRepo.findAll(runDetailsQueryExample, Sort.by("runId"));
			}
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 10,
					Sort.by("runId").descending());
			if (request.isOnlyEventRunsRequired()) {
				Page<RunDetails> page = runDetailsRepo.findAllEventRuns(pageRequest);
				runDetailsList = page.getContent();
			} else {
				Page<RunDetails> page = runDetailsRepo.findAll(runDetailsQueryExample, pageRequest);
				runDetailsList = page.getContent();
			}
		}

		// populateEventDetailsIfApplicable(runDetailsList);

		response.setRunDetailsList(runDetailsList);
		if (CollectionUtils.isEmpty(runDetailsList) || runDetailsList.size() < 10) {
			response.setMoreContentAvailable(false);
		} else {
			response.setMoreContentAvailable(true);
		}
		logger.info("GetRunsForUserRequestProcessor doProcessing Completed for User Id " + request.getUserId());
		return response;
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
