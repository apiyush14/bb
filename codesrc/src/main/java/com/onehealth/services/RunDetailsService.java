package com.onehealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.service.BaseService;
import com.onehealth.model.request.AddRunDetailsRequest;
import com.onehealth.model.request.GetRunsForUserRequest;
import com.onehealth.processors.AddRunsRequestProcessor;
import com.onehealth.processors.GetRunsForUserRequestProcessor;

@RestController
public class RunDetailsService extends BaseService {

	@Autowired
	AddRunsRequestProcessor addRunsRequestProcessor;

	@Autowired
	GetRunsForUserRequestProcessor getRunsForUserRequestProcessor;

	@PostMapping(path = "run-details/addRuns/{userId}", produces = "application/json")
	public ResponseEntity<Object> addRuns(@PathVariable String userId, @RequestBody AddRunDetailsRequest runDetails) {
		AddRunDetailsRequest request = new AddRunDetailsRequest();
		request.setUserId(userId);
		request.setRunDetailsList(runDetails.getRunDetailsList());
		addRunsRequestProcessor.setRequest(request);
		return execute(addRunsRequestProcessor);
	}

	@GetMapping(path = "run-details/getRuns/{userId}", produces = "application/json")
	public ResponseEntity<Object> getRunsForUser(@PathVariable String userId,
			@RequestParam(required = false, name = "page") String pageNumber) {
		GetRunsForUserRequest request = new GetRunsForUserRequest();
		request.setUserId(userId);
		request.setPageNumber(pageNumber);
		getRunsForUserRequestProcessor.setRequest(request);
		return execute(getRunsForUserRequestProcessor);
	}

}
