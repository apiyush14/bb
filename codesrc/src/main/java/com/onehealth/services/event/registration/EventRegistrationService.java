package com.onehealth.services.event.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.service.BaseService;
import com.onehealth.entities.event.registration.EventRegistrationDetails;
import com.onehealth.model.event.registration.RegisterUserForEventRequest;
import com.onehealth.processors.event.registration.GetRegisteredEventsForUserRequestProcessor;
import com.onehealth.processors.event.registration.RegisterUserForEventRequestProcessor;

@RestController
public class EventRegistrationService extends BaseService {

	@Autowired
	RegisterUserForEventRequestProcessor registerUserForEventRequestProcessor;

	@Autowired
	GetRegisteredEventsForUserRequestProcessor getRegisteredEventsForUserRequestProcessor;

	@PostMapping(path = "event-registration/registerForEvent/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUserForEvent(@PathVariable Long eventId,
			@RequestParam(required = false, name = "userId") String userId) {
		RegisterUserForEventRequest request = new RegisterUserForEventRequest();
		EventRegistrationDetails eventRegistrationDetails = new EventRegistrationDetails();
		eventRegistrationDetails.setEventId(eventId);
		eventRegistrationDetails.setUserId(userId);
		request.setEventRegistrationDetails(eventRegistrationDetails);
		registerUserForEventRequestProcessor.setRequest(request);
		return execute(registerUserForEventRequestProcessor);
	}

	@GetMapping(path = "event-registration/getRegisteredEventsForUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRegisteredEventsForUser(@PathVariable String userId) {
		BaseRequestProcessorInput request = new BaseRequestProcessorInput();
		request.setUserId(userId);
		getRegisteredEventsForUserRequestProcessor.setRequest(request);
		return execute(getRegisteredEventsForUserRequestProcessor);
	}

}
