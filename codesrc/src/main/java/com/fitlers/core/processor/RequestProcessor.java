package com.fitlers.core.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fitlers.model.request.GetUserGoogleFitDataRequest;
import com.fitlers.model.response.GetUserGoogleFitDataResponse;

public abstract class RequestProcessor<req extends Object, res extends Object> {

	private req request;
	private static final Logger LOG = LoggerFactory.getLogger(RequestProcessor.class);

	public void setRequest(req request) {
		this.request = request;
	}

	public res handleRequest() throws Exception {
		res response = null;
		try {
			preProcess(request);
			response = doProcessing(request);
			postProcess(response);
		} catch (Exception e) {
			LOG.error("Request Processor failed while processing request " + e.getMessage());
			throw e;
		}
		return response;
	}

	public boolean isRequestValid(req request) throws Exception {
		return true;
	}

	public void preProcess(req request) {
		try {
			isRequestValid(request);
		} catch (Exception e) {
			LOG.error("Request Processor failed while input validation " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	public abstract res doProcessing(req request) throws Exception;

	public void postProcess(res response) {
	}

	
}
