package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.LogClientErrorRequest;
import com.fitlers.processors.ClientLoggingRequestProcessor;

@RestController
public class LoggingService extends BaseService {

	@Autowired
	private ClientLoggingRequestProcessor clientLoggingRequestProcessor;

	@PostMapping(path = "log/client/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logClientError(@PathVariable String userId,
			@RequestBody LogClientErrorRequest logClientErrorRequest) {
		LogClientErrorRequest request = new LogClientErrorRequest();
		request.setUserId(userId);
		request.setErrorMessage(logClientErrorRequest.getErrorMessage());
		request.setStackTrace(logClientErrorRequest.getStackTrace());
		clientLoggingRequestProcessor.setRequest(request);
		return execute(clientLoggingRequestProcessor);
	}
}
