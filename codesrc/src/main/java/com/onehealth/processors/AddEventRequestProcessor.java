package com.onehealth.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.model.request.AddEventDetailsRequest;
import com.onehealth.repo.EventDetailsRepository;

@Component
public class AddEventRequestProcessor extends RequestProcessor<AddEventDetailsRequest, Long> {

	@Autowired
	EventDetailsRepository eventDetailsRepository;

	@Override
	public Long doProcessing(AddEventDetailsRequest request) throws Exception {
		EventDetails eventDetails = eventDetailsRepository.save(request.getEventDetails());
		return eventDetails.getEventId();
	}

}
