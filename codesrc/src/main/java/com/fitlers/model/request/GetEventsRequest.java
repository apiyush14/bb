package com.fitlers.model.request;

import com.fitlers.core.model.BaseRequestProcessorInput;

public class GetEventsRequest extends BaseRequestProcessorInput {
	private String pageNumber;

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

}
