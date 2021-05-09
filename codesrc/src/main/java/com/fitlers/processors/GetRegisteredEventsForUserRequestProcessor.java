package com.fitlers.processors;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.EventRegistrationDetails;
import com.fitlers.model.request.GetEventsRequest;
import com.fitlers.model.response.GetEventRegistrationDetailsResponse;
import com.fitlers.repo.EventRegistrationDetailsRepository;

@Component
public class GetRegisteredEventsForUserRequestProcessor
		extends RequestProcessor<GetEventsRequest, GetEventRegistrationDetailsResponse> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;

	public static final Logger logger = LoggerFactory.getLogger(GetRegisteredEventsForUserRequestProcessor.class);

	@Override
	public GetEventRegistrationDetailsResponse doProcessing(GetEventsRequest request) throws Exception {
		logger.info(
				"GetRegisteredEventsForUserRequestProcessor doProcessing Started for User Id " + request.getUserId());
		GetEventRegistrationDetailsResponse response = new GetEventRegistrationDetailsResponse();
		EventRegistrationDetails eventRegistrationDetailsQueryObj = new EventRegistrationDetails();
		eventRegistrationDetailsQueryObj.setUserId(request.getUserId());
		Example<EventRegistrationDetails> eventRegistrationDetailsQueryExample = Example
				.of(eventRegistrationDetailsQueryObj);
		List<EventRegistrationDetails> eventRegistrationDetailsList;

		if (Objects.isNull(request.getPageNumber())) {
			eventRegistrationDetailsList = eventRegistrationRepository
					.findAllEligibleEvents(eventRegistrationDetailsQueryExample, Sort.by("eventId"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 10, Sort.by("eventId"));
			Page<EventRegistrationDetails> page = eventRegistrationRepository
					.findAllEligibleEvents(eventRegistrationDetailsQueryExample, pageRequest);
			eventRegistrationDetailsList = page.getContent();
		}

		if (!Objects.isNull(eventRegistrationDetailsList) && (!eventRegistrationDetailsList.isEmpty())) {
			eventRegistrationDetailsList = eventRegistrationDetailsList.stream()
					.filter(eventDetails -> eventDetails.getUserId().equals(request.getUserId()))
					.collect(Collectors.toList());
		}
		
		response.setEventRegistrationDetails(eventRegistrationDetailsList);
		if (CollectionUtils.isEmpty(eventRegistrationDetailsList) || eventRegistrationDetailsList.size() < 10) {
			response.setMoreContentAvailable(false);
		} else {
			response.setMoreContentAvailable(true);
		}
		logger.info(
				"GetRegisteredEventsForUserRequestProcessor doProcessing Completed for User Id " + request.getUserId());
		return response;
	}

}
