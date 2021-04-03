package com.fitlers.model.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadEventImageRequest {
	private MultipartFile file;
	private Long eventId;
	private String imageType;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

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
