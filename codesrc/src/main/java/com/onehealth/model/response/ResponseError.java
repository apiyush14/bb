package com.onehealth.model.response;

public class ResponseError {
	
	private String uri;
	private String error;
	private String message;
	private int statusCode;
	

	public ResponseError(String uri, String error, String message, int statusCode) {
		this.uri = uri;
		this.error = error;
		this.message = message;
		this.statusCode = statusCode;
	}
	

	
}
