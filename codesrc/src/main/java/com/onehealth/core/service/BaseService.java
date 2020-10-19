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
		try {
			return requestProcessor.handleRequest();
		} catch (ResponseStatusException e) {
			throw e;
		} catch (NoDataFoundException e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
		}
	}

	public void postExecute() {

	}

}
