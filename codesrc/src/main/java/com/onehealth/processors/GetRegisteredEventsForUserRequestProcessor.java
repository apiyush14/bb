package com.onehealth.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.entities.EventRegistrationDetails;
import com.onehealth.model.request.GetEventsRequest;
import com.onehealth.model.response.GetEventDetailsResponse;
import com.onehealth.repo.EventRegistrationDetailsRepository;

@Component
public class GetRegisteredEventsForUserRequestProcessor
		extends RequestProcessor<GetEventsRequest, GetEventDetailsResponse> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;

	@Override
	public GetEventDetailsResponse doProcessing(GetEventsRequest request) throws Exception {
		GetEventDetailsResponse response = new GetEventDetailsResponse();
		EventRegistrationDetails eventRegistrationDetailsQueryObj = new EventRegistrationDetails();
		eventRegistrationDetailsQueryObj.setUserId(request.getUserId());
		Example<EventRegistrationDetails> eventRegistrationDetailsQueryExample = Example
				.of(eventRegistrationDetailsQueryObj);
		List<EventRegistrationDetails> eventRegistrationDetailsList;

		if (Objects.isNull(request.getPageNumber())) {
			eventRegistrationDetailsList=eventRegistrationRepository.findAllEligibleEvents(eventRegistrationDetailsQueryExample, Sort.by("eventId"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3, Sort.by("eventId"));
			Page<EventRegistrationDetails> page = eventRegistrationRepository
					.findAllEligibleEvents(eventRegistrationDetailsQueryExample, pageRequest);
			eventRegistrationDetailsList = page.getContent();
		}

		if (!Objects.isNull(eventRegistrationDetailsList) && (!eventRegistrationDetailsList.isEmpty())) {
			List<EventDetails> eventDetailsList = eventRegistrationDetailsList.stream()
					.map(event -> event.getEventDetails()).collect(Collectors.toList());
			response.setEventDetails(eventDetailsList);
		} else {
			response.setEventDetails(new ArrayList<EventDetails>());
		}
		return response;
	}

}
