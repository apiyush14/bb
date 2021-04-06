package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.GetEventResultDetailsForEventRequest;
import com.fitlers.model.request.GetEventResultDetailsRequest;
import com.fitlers.processors.GetEventResultDetailsForEventRequestProcessor;
import com.fitlers.processors.GetEventResultDetailsRequestProcessor;

@RestController
public class EventResultDetailsService extends BaseService {

	@Autowired
	private GetEventResultDetailsRequestProcessor getEventResultDetailsRequestProcessor;

	@Autowired
	private GetEventResultDetailsForEventRequestProcessor getEventResultDetailsForEventRequestProcessor;

	@GetMapping(path = "event-results/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEventResultDetailsForUser(@PathVariable String userId) {
		GetEventResultDetailsRequest request = new GetEventResultDetailsRequest();
		request.setUserId(userId);
		getEventResultDetailsRequestProcessor.setRequest(request);
		return execute(getEventResultDetailsRequestProcessor);
	}

	@GetMapping(path = "event-results/eventId/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEventResultDetailsForEvent(@PathVariable String eventId,
			@RequestParam(required = false, name = "page") String pageNumber) {
		GetEventResultDetailsForEventRequest request = new GetEventResultDetailsForEventRequest();
		request.setEventId(eventId);
		request.setPageNumber(pageNumber);
		getEventResultDetailsForEventRequestProcessor.setRequest(request);
		return execute(getEventResultDetailsForEventRequestProcessor);
	}
}
