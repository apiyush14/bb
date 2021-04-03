package com.fitlers.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.fitlers.core.exceptions.NoDataFoundException;
import com.fitlers.core.processor.RequestProcessor;

public class BaseService {

	private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

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
			LOG.error("Service failed while processing request " + e.getMessage());
			throw e;
		} catch (NoDataFoundException e) {
			LOG.error("Service failed while processing request " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Service failed while processing request " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
		}
	}

	public void postExecute() {

	}

}
