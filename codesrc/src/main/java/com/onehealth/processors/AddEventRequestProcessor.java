package com.onehealth.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public static final Logger logger = LoggerFactory.getLogger(AddEventRequestProcessor.class);

	@Override
	public Long doProcessing(AddEventDetailsRequest request) throws Exception {
		logger.info("AddEventRequestProcessor doProcessing Started for Event Id " + request.getEventDetails().getEventId());
		EventDetails eventDetails = eventDetailsRepository.save(request.getEventDetails());
		logger.info("AddEventRequestProcessor doProcessing Completed for Event Id " + request.getEventDetails().getEventId());
		return eventDetails.getEventId();
	}

}
