package com.onehealth.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.core.exceptions.NoDataFoundException;
import com.onehealth.core.exceptions.NoRunDetailsFoundException;
import com.onehealth.core.exceptions.NoUserFoundException;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.entities.EventResultDetails;
import com.onehealth.entities.EventResultDetailsId;
import com.onehealth.entities.RunDetails;
import com.onehealth.model.request.GetRunsForUserRequest;
import com.onehealth.model.response.GetRunsForUserResponse;
import com.onehealth.repo.EventDetailsRepository;
import com.onehealth.repo.EventResultDetailsRepository;
import com.onehealth.repo.RunDetailsRepository;

@Component
public class GetRunsForUserRequestProcessor extends RequestProcessor<GetRunsForUserRequest, GetRunsForUserResponse> {

	@Autowired
	private RunDetailsRepository runDetailsRepo;
	@Autowired
	private EventDetailsRepository eventDetailsRepo;
	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepo;

	private static final Logger LOG = Logger.getLogger(GetRunsForUserRequestProcessor.class);

	@Override
	public boolean isRequestValid(GetRunsForUserRequest request) {
		if (StringUtils.isEmpty(request.getUserId())) {
			LOG.error("Invalid user id found, unable to process request");
			throw new NoUserFoundException();
		}
		LOG.debug("Valid get run(s) details request, processing..");
		return super.isRequestValid(request);
	}

	@Override
	public GetRunsForUserResponse doProcessing(GetRunsForUserRequest request) {
		GetRunsForUserResponse response = new GetRunsForUserResponse();
		RunDetails runDetailsQueryObj = new RunDetails();
		runDetailsQueryObj.setUserId(request.getUserId());
		Example<RunDetails> runDetailsQueryExample = Example.of(runDetailsQueryObj);
		List<RunDetails> runDetailsList;
		LOG.debug("Retrieving all saved run(s).");
		
		if (Objects.isNull(request.getPageNumber())) {
			runDetailsList = runDetailsRepo.findAll(runDetailsQueryExample, Sort.by("runId"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("runId").descending());
			Page<RunDetails> page = runDetailsRepo.findAll(runDetailsQueryExample, pageRequest);
			runDetailsList = page.getContent();
		}

		//populateEventDetailsIfApplicable(runDetailsList);
		if(Objects.nonNull(runDetailsList) && (!runDetailsList.isEmpty())) {
		LOG.debug("Retrieved "+runDetailsList.size()+" saved run(s).");
		response.setRunDetailsList(runDetailsList);
		}
		else
			throw new NoRunDetailsFoundException();
		
		return response;
	}

	private void populateEventDetailsIfApplicable(List<RunDetails> runDetailsList) {
		Map<Long, EventDetails> mapOfEventDetails = new HashMap<Long, EventDetails>();
		Map<EventResultDetailsId, EventResultDetails> mapOfEventResultDetails = new HashMap<EventResultDetailsId, EventResultDetails>();
		runDetailsList.parallelStream().filter(run -> !Objects.isNull(run.getEventId())).forEach(eventRun -> {
			mapOfEventDetails.putIfAbsent(eventRun.getEventId(), null);
			mapOfEventResultDetails.putIfAbsent(new EventResultDetailsId(eventRun.getUserId(), eventRun.getEventId()),
					null);
		});

		List<EventDetails> listOfEventDetails = eventDetailsRepo.findAllById(mapOfEventDetails.keySet());
		List<EventResultDetails> listOfEventResultDetails = eventResultDetailsRepo
				.findAllById(mapOfEventResultDetails.keySet());

		listOfEventDetails.parallelStream().forEach(eventDetail -> {
			mapOfEventDetails.put(eventDetail.getEventId(), eventDetail);
		});

		listOfEventResultDetails.parallelStream().forEach(eventResultDetail -> {
			mapOfEventResultDetails.put(
					new EventResultDetailsId(eventResultDetail.getUserId(), eventResultDetail.getEventId()),
					eventResultDetail);
		});

		runDetailsList.parallelStream().filter(run -> !Objects.isNull(run.getEventId())).forEach(eventRun -> {
			eventRun.setEventDetails(mapOfEventDetails.get(eventRun.getEventId()));
			eventRun.setEventResultDetails(
					mapOfEventResultDetails.get(new EventResultDetailsId(eventRun.getUserId(), eventRun.getEventId())));
		});
	}

}
