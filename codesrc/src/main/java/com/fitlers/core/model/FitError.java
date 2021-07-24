package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FitError {
	
	@JsonProperty("return")
private int code;
private String message;
private FitErrors errors;
private String status;
public int getCode() {
	return code;
}
public String getMessage() {
	return message;
}
public FitErrors getErrors() {
	return errors;
}
public String getStatus() {
	return status;
}
public void setCode(int code) {
	this.code = code;
}
public void setMessage(String message) {
	this.message = message;
}
public void setErrors(FitErrors errors) {
	this.errors = errors;
}
public void setStatus(String status) {
	this.status = status;
}

}
