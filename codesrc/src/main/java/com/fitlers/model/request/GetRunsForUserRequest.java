package com.fitlers.model.request;

import com.fitlers.core.model.BaseRequestProcessorInput;

public class GetRunsForUserRequest extends BaseRequestProcessorInput {
	
	private boolean isOnlyEventRunsRequired;
	private String pageNumber;
	
	public boolean isOnlyEventRunsRequired() {
		return isOnlyEventRunsRequired;
	}

	public void setOnlyEventRunsRequired(boolean isOnlyEventRunsRequired) {
		this.isOnlyEventRunsRequired = isOnlyEventRunsRequired;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

}
