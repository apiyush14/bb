package com.fitlers.model.request;

public class GetDisplayImageForEventRequest {
	private Long eventId;
	private String imageType;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

}
