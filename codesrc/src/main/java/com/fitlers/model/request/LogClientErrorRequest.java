package com.fitlers.model.request;

import com.fitlers.core.model.BaseRequestProcessorInput;

public class LogClientErrorRequest extends BaseRequestProcessorInput {

	private String stackTrace;
	private String errorMessage;

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
