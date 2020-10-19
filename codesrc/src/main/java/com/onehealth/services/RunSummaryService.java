package com.onehealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.service.BaseService;
import com.onehealth.processors.GetRunSummaryForUserRequestProcessor;

@RestController
public class RunSummaryService extends BaseService {
	
	@Autowired
	GetRunSummaryForUserRequestProcessor getRunSummaryForUserRequestProcessor;
	
	@GetMapping(path = "run-details/getRunSummary/{userId}", produces = "application/json")
	public ResponseEntity<Object> getRunsForUser(@PathVariable String userId) {
		BaseRequestProcessorInput request = new BaseRequestProcessorInput();
		request.setUserId(userId);
		getRunSummaryForUserRequestProcessor.setRequest(request);
		return execute(getRunSummaryForUserRequestProcessor);
	}
}
