package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.entities.EventRegistrationDetails;
import com.fitlers.model.request.GetEventsRequest;
import com.fitlers.model.request.RegisterUserForEventRequest;
import com.fitlers.processors.GetRegisteredEventsForUserRequestProcessor;
import com.fitlers.processors.RegisterUserForEventRequestProcessor;

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
	public ResponseEntity<Object> getRegisteredEventsForUser(@PathVariable String userId,
			@RequestParam(required = false, name = "page") String pageNumber) {
		GetEventsRequest request = new GetEventsRequest();
		request.setUserId(userId);
		request.setPageNumber(pageNumber);
		getRegisteredEventsForUserRequestProcessor.setRequest(request);
		return execute(getRegisteredEventsForUserRequestProcessor);
	}

}
