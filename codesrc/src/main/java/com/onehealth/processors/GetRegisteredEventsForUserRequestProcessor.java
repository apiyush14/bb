package com.onehealth.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.entities.EventRegistrationDetails;
import com.onehealth.model.response.GetEventDetailsResponse;
import com.onehealth.repo.EventRegistrationDetailsRepository;

@Component
public class GetRegisteredEventsForUserRequestProcessor
		extends RequestProcessor<BaseRequestProcessorInput, GetEventDetailsResponse> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;

	@Override
	public GetEventDetailsResponse doProcessing(BaseRequestProcessorInput request) throws Exception {
		GetEventDetailsResponse response = new GetEventDetailsResponse();
		EventRegistrationDetails eventRegistrationDetailsQueryObj = new EventRegistrationDetails();
		eventRegistrationDetailsQueryObj.setUserId(request.getUserId());
		Example<EventRegistrationDetails> eventRegistrationDetailsQueryExample = Example
				.of(eventRegistrationDetailsQueryObj);
		List<EventRegistrationDetails> eventRegistrationDetailsList;
		eventRegistrationDetailsList = eventRegistrationRepository.findAll(eventRegistrationDetailsQueryExample);
		if (!Objects.isNull(eventRegistrationDetailsList) && (!eventRegistrationDetailsList.isEmpty())) {
			List<EventDetails> eventDetailsList = eventRegistrationDetailsList.stream()
					.map(event -> event.getEventDetails()).collect(Collectors.toList());
			response.setEventDetails(eventDetailsList);
		}
		else {
			response.setEventDetails(new ArrayList<EventDetails>());
		}
		return response;
	}

}
