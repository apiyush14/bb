package com.onehealth.model.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadEventImageRequest {
	private MultipartFile file;
	private Long eventId;
	
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
}
