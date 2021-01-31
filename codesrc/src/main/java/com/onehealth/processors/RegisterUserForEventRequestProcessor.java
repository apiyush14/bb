package com.onehealth.processors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.UserDetails;
import com.onehealth.model.request.RegisterUserForEventRequest;
import com.onehealth.repo.EventRegistrationDetailsRepository;

@Component
public class RegisterUserForEventRequestProcessor extends RequestProcessor<RegisterUserForEventRequest, Boolean> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;
	
	private static final Logger LOG = Logger.getLogger(RegisterUserForEventRequestProcessor.class);

	@Override
	public Boolean doProcessing(RegisterUserForEventRequest request) {
		UserDetails user = request.getEventRegistrationDetails().getUserDetails();
		LOG.debug("Registering user :"
				+user.getUserFirstName()+" "+user.getUserLastName()+" for event :"+request.getEventRegistrationDetails().getEventDetails().getEventName());
		eventRegistrationRepository.save(request.getEventRegistrationDetails());
		LOG.debug("User registered successfully.");
		return true;
	}

}
