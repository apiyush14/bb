package com.onehealth.core.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.onehealth.core.exceptions.NoDataFoundException;
import com.onehealth.core.processor.RequestProcessor;

public class BaseService {

	public void preExecute() {

	}

	public ResponseEntity<Object> execute(RequestProcessor requestProcessor) {
		preExecute();
		Object responseObject = generateResponse(requestProcessor);
		postExecute();
		return ResponseEntity.ok().body(responseObject);
	}

	private Object generateResponse(RequestProcessor requestProcessor) {
			return requestProcessor.handleRequest();
	}

	public void postExecute() {

	}

}
