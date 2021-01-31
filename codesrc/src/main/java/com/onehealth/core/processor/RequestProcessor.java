package com.onehealth.core.processor;

import com.onehealth.core.exceptions.InValidRequestException;

public abstract class RequestProcessor<req extends Object, res extends Object> {

	private req request;

	public void setRequest(req request) {
		this.request = request;
	}

	public res handleRequest() {
		res response = null;

		preProcess(request);
		response = doProcessing(request);
		postProcess(response);
		return response;
	}

	public boolean isRequestValid(req request) {

		boolean isReqValid = true; 
		if(!isReqValid)
			throw new InValidRequestException();

		return isReqValid;
	}

	public void preProcess(req request) {
		
		isRequestValid(request);
		
	}

	public abstract res doProcessing(req request);

	public void postProcess(res response) {
	}
}
