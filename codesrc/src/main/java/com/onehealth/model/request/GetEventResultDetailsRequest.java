package com.onehealth.model.request;

import com.onehealth.core.model.BaseRequestProcessorInput;

public class GetEventResultDetailsRequest extends BaseRequestProcessorInput {

	private Long eventId;
	private Integer pageNumber;
	private Integer pageSize;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
