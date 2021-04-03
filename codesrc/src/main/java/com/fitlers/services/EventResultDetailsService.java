package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.GetEventResultDetailsRequest;
import com.fitlers.processors.GetEventResultDetailsRequestProcessor;

@RestController
public class EventResultDetailsService extends BaseService {

	@Autowired
	private GetEventResultDetailsRequestProcessor getEventResultDetailsRequestProcessor;

	@GetMapping(path = "event-results/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEventResultDetailsForUser(@PathVariable String userId) {
		GetEventResultDetailsRequest request = new GetEventResultDetailsRequest();
		request.setUserId(userId);
		getEventResultDetailsRequestProcessor.setRequest(request);
		return execute(getEventResultDetailsRequestProcessor);
	}

	/*@GetMapping(path = EVENT_RESULTS + "/{eventId}")
	public ResponseEntity<Object> getEventResultDetailsPageable(@PathVariable(required = true) Long eventId,
			@RequestParam(required = true) Integer pageNumber, @RequestParam(required = true) Integer pageSize)
			throws NoDataFoundException {

		GetEventResultDetailsRequest request = new GetEventResultDetailsRequest();
		request.setEventId(eventId);
		request.setPageNumber(pageNumber);
		request.setPageSize(pageSize);

		GetEventResultDetailsResponse eventResultDetailsResponse = getEventResultDetailsRequestProcessor
				.getEventResultDetailsPageable(request);
		return ResponseEntity.ok(eventResultDetailsResponse);
	}*/
}
