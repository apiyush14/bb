package com.onehealth.services.event.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.service.BaseService;
import com.onehealth.entities.event.registration.EventRegistrationDetails;
import com.onehealth.model.event.registration.RegisterUserForEventRequest;
import com.onehealth.processors.event.registration.RegisterUserForEventRequestProcessor;

@RestController
public class EventRegistrationService extends BaseService {

@Autowired
RegisterUserForEventRequestProcessor registerUserForEventRequestProcessor;

@PostMapping(path = "event-registration/registerForEvent/{eventId}", produces = "application/json")
public ResponseEntity<Object> registerUserForEvent(@PathVariable Long eventId,@RequestParam(required = false, name = "userId") String userId) {
	RegisterUserForEventRequest request=new RegisterUserForEventRequest();
	EventRegistrationDetails eventRegistrationDetails=new EventRegistrationDetails();
	eventRegistrationDetails.setEventId(eventId);
	eventRegistrationDetails.setUserId(userId);
	request.setEventRegistrationDetails(eventRegistrationDetails);
	registerUserForEventRequestProcessor.setRequest(request);
	return execute(registerUserForEventRequestProcessor);
}
}
