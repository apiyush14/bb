package com.onehealth.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.RegisterUserForEventRequest;
import com.onehealth.repo.EventRegistrationDetailsRepository;

@Component
public class RegisterUserForEventRequestProcessor extends RequestProcessor<RegisterUserForEventRequest, Boolean> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;

	public static final Logger logger = LoggerFactory.getLogger(RegisterUserForEventRequestProcessor.class);

	@Override
	public Boolean doProcessing(RegisterUserForEventRequest request) throws Exception {
		logger.info("RegisterUserForEventRequestProcessor doProcessing Started for User Id "
				+ request.getEventRegistrationDetails().getUserId() + " for event id "
				+ request.getEventRegistrationDetails().getEventId());
		request.getEventRegistrationDetails().setRunId((long) 0);
		eventRegistrationRepository.save(request.getEventRegistrationDetails());
		logger.info("RegisterUserForEventRequestProcessor doProcessing Completed for User Id "
				+ request.getEventRegistrationDetails().getUserId() + " for event id "
				+ request.getEventRegistrationDetails().getEventId());
		return true;
	}

}
