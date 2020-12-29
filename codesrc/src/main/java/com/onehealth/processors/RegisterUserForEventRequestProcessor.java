package com.onehealth.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.RegisterUserForEventRequest;
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
