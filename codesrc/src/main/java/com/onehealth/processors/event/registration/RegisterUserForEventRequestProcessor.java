package com.onehealth.processors.event.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.event.registration.RegisterUserForEventRequest;
import com.onehealth.repo.EventRegistrationDetailsRepository;

@Component
public class RegisterUserForEventRequestProcessor extends RequestProcessor<RegisterUserForEventRequest, Boolean> {

	@Autowired
	EventRegistrationDetailsRepository eventRegistrationRepository;
	
	@Override
	public Boolean doProcessing(RegisterUserForEventRequest request) throws Exception {
		eventRegistrationRepository.save(request.getEventRegistrationDetails());
		return true;
	}

}
