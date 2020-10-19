package com.onehealth.core.processor;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class RequestProcessor<req extends Object, res extends Object> {

	private req request;

	public void setRequest(req request) {
		this.request = request;
	}

	public res handleRequest() throws Exception{
		res response = null;
		try {
			preProcess(request);
			response = doProcessing(request);
			postProcess(response);
		} catch (Exception e) {
            throw e;
		}
		return response;
	}

	public boolean isRequestValid(req request) throws Exception{
		return true;
	}

	public void preProcess(req request) {
		try {
			isRequestValid(request);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	public abstract res doProcessing(req request) throws Exception;

	public void postProcess(res response) {
	}
}
