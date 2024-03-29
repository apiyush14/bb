package com.fitlers.core.exceptions;

public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}
}
