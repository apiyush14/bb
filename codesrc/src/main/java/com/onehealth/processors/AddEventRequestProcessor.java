package com.onehealth.processors;

import org.apache.log4j.Logger;
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
	
	private static final Logger LOG = Logger.getLogger(AddEventRequestProcessor.class);

	@Override
	public Long doProcessing(AddEventDetailsRequest request) {
		LOG.info("Saving "+request.getEventDetails().getEventName()+" Event Details.");
		EventDetails eventDetails = eventDetailsRepository.save(request.getEventDetails());
		LOG.info("Event Details saved for"+eventDetails.getEventOrganizerFirstName() + " "+eventDetails.getEventOrganizerLastName());
		return eventDetails.getEventId();
	}

}
