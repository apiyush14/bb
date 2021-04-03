package com.fitlers.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.EventDetails;
import com.fitlers.model.request.AddEventDetailsRequest;
import com.fitlers.repo.EventDetailsRepository;

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
