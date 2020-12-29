package com.onehealth.model.request;

import com.onehealth.core.model.BaseRequestProcessorInput;

public class GetRunsForUserRequest extends BaseRequestProcessorInput {
 private String pageNumber;

public String getPageNumber() {
	return pageNumber;
}

public void setPageNumber(String pageNumber) {
	this.pageNumber = pageNumber;
}
 
}
