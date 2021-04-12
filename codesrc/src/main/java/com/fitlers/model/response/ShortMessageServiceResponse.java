package com.fitlers.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortMessageServiceResponse {

	@JsonProperty("return")
	private String status;
	private String request_id;
	private List<String> message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

}
