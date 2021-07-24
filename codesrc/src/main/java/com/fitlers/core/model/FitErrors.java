package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FitErrors {

	
	@JsonProperty("return")
	private String message;
	private String domain;
	private String reason;
	private String location;
	private String locationType;
	public String getMessage() {
		return message;
	}
	public String getDomain() {
		return domain;
	}
	public String getReason() {
		return reason;
	}
	public String getLocation() {
		return location;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
}
