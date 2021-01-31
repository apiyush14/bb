package com.onehealth.error;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.kafka.common.errors.SerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.onehealth.core.exceptions.InValidRequestException;
import com.onehealth.core.exceptions.MediaFileStorageException;
import com.onehealth.core.exceptions.NoDataFoundException;
import com.onehealth.core.exceptions.NoEventDetailsFoundException;
import com.onehealth.core.exceptions.NoRunDetailsFoundException;
import com.onehealth.core.exceptions.NoRunSummryFoundException;
import com.onehealth.core.exceptions.NoUserFoundException;
import com.onehealth.model.response.ResponseError;

@ControllerAdvice
public class GlobalErrorResponseHandler {

	private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
	private static final String ILLEGAL_ARGUMENT_ERROR_MESSAGE = "Illegal Value for the Argument";
	private static final String INVALID_ARGUMENT_ERROR_MESSAGE = "Invalid Value for the Argument";
	private static final String REQUEST_METHOD_NOT_ALLOWED_ERROR_MESSAGE = "Request method Not allowed";
	private static final String UNSUPPORTED_MEDIA_ERROR_MESSAGE = "Unsupported Media Exception";
	private static final String MISSING_PARAMS_ERROR_MESSAGE = "Missing Param(s)";
	private static final String BAD_CREDENTIALS = "Bad Credentials";
	private static final String REQUEST_BODY_ERROR_MESSAGE = "Body Object Format Error";
	private static final String NO_ENTITY_FOUND_ERROR_MESSAGE = "No Entity Found";
	private static final String SERIALIZATION_ERROR_MESSAGE = "Error Occured during Serialization";
	private static final String INVALID_REQ_ERROR_MESSAGE = "Invalid Request";
	private static final String NO_USER_FOUND_ERROR_MESSAGE = "User not found";
	private static final String NO_EVENT_DETAILS_FOUND_ERROR_MESSAGE = "No Event Details Found";
	private static final String NO_RUN_DETAILS_FOUND_ERROR_MESSAGE = "No Run Details Found for requested user";
	private static final String NO_RUN_SUMMARY_FOUND_ERROR_MESSAGE = "No Run Summary Found for requested user";
	private static final String IO_EXCEPTION_FOUND_ERROR_MESSAGE = "IO Exception occured during proocessing request";
	private static final String MEDIA_FILE_EXCEPTION_ERROR_MESSAGE = "Error occured during processing media file";
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity illegalArgumentException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), ILLEGAL_ARGUMENT_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(InValidRequestException.class)
	public ResponseEntity invalidReqException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), INVALID_REQ_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity illegalMethodArgumentException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), ILLEGAL_ARGUMENT_ERROR_MESSAGE);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity reqMethodNotAllowedException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.METHOD_NOT_ALLOWED.value(), REQUEST_METHOD_NOT_ALLOWED_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity missingRequestParamException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), MISSING_PARAMS_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity unsupportedMediaException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), UNSUPPORTED_MEDIA_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity numberFormatException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), ILLEGAL_ARGUMENT_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity nullPointerException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity bindException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), INVALID_ARGUMENT_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity badCredentialsException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), REQUEST_BODY_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity noDataFoundException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.NOT_FOUND.value(), NO_ENTITY_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NoUserFoundException.class)
	public ResponseEntity noUserFoundException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.BAD_REQUEST.value(), NO_USER_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(SerializationException.class)
	public ResponseEntity serializationException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), SERIALIZATION_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NoEventDetailsFoundException.class)
	public ResponseEntity noEventDetaisFoundException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.NOT_FOUND.value(), NO_EVENT_DETAILS_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NoRunDetailsFoundException.class)
	public ResponseEntity noRunDetaisFoundException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.NOT_FOUND.value(), NO_RUN_DETAILS_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(NoRunSummryFoundException.class)
	public ResponseEntity noRunSummaryFoundException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.NOT_FOUND.value(), NO_RUN_SUMMARY_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity ioException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), IO_EXCEPTION_FOUND_ERROR_MESSAGE);
	}
	
	@ExceptionHandler(MediaFileStorageException.class)
	public ResponseEntity mediaFileStorageException(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), MEDIA_FILE_EXCEPTION_ERROR_MESSAGE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity internalServerError(final HttpServletRequest request) {
		return sendErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR_MESSAGE);
	}

	private ResponseEntity sendErrorResponse(HttpServletRequest request, int statusCode, String errorMsg) {
		String error = HttpStatus.resolve(statusCode).getReasonPhrase();
		String path = request.getRequestURI();
		return ResponseEntity.status(statusCode).body(new ResponseError(path, error, errorMsg, statusCode));

	}
}
