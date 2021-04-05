package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.AddRunDetailsRequest;
import com.fitlers.model.request.GetRunsForUserRequest;
import com.fitlers.processors.AddRunsRequestProcessor;
import com.fitlers.processors.GetRunsForUserRequestProcessor;

@RestController
public class RunDetailsService extends BaseService {

	@Autowired
	AddRunsRequestProcessor addRunsRequestProcessor;

	@Autowired
	GetRunsForUserRequestProcessor getRunsForUserRequestProcessor;

	@PostMapping(path = "run-details/addRuns/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addRuns(@PathVariable String userId, @RequestBody AddRunDetailsRequest runDetails) {
		AddRunDetailsRequest request = new AddRunDetailsRequest();
		request.setUserId(userId);
		request.setRunDetailsList(runDetails.getRunDetailsList());
		addRunsRequestProcessor.setRequest(request);
		return execute(addRunsRequestProcessor);
	}

	@GetMapping(path = "run-details/getRuns/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRunsForUser(@PathVariable String userId,
			@RequestParam(required = false, name = "page") String pageNumber,
			@RequestParam(required = false, name = "isOnlyEventRunsRequired") boolean isOnlyEventRunsRequired) {
		GetRunsForUserRequest request = new GetRunsForUserRequest();
		request.setUserId(userId);
		request.setOnlyEventRunsRequired(isOnlyEventRunsRequired);
		request.setPageNumber(pageNumber);
		getRunsForUserRequestProcessor.setRequest(request);
		return execute(getRunsForUserRequestProcessor);
	}

}
