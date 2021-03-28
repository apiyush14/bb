package com.onehealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.service.BaseService;
import com.onehealth.model.request.GetEventResultDetailsRequest;
import com.onehealth.processors.GetEventResultDetailsRequestProcessor;

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

}
