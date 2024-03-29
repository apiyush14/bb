package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.model.BaseRequestProcessorInput;
import com.fitlers.core.service.BaseService;
import com.fitlers.processors.GetRunSummaryForUserRequestProcessor;

@RestController
public class RunSummaryService extends BaseService {

	@Autowired
	GetRunSummaryForUserRequestProcessor getRunSummaryForUserRequestProcessor;

	@GetMapping(path = "run-details/getRunSummary/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRunsForUser(@PathVariable String userId) {
		BaseRequestProcessorInput request = new BaseRequestProcessorInput();
		request.setUserId(userId);
		getRunSummaryForUserRequestProcessor.setRequest(request);
		return execute(getRunSummaryForUserRequestProcessor);
	}
}
