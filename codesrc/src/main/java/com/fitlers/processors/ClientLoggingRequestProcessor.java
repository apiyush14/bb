package com.fitlers.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.LogClientErrorRequest;

@Component
public class ClientLoggingRequestProcessor extends RequestProcessor<LogClientErrorRequest, Boolean> {

	public static final Logger logger = LoggerFactory.getLogger(ClientLoggingRequestProcessor.class);

	@Override
	public Boolean doProcessing(LogClientErrorRequest request) throws Exception {
		logger.info("Request failed for client : " + request.getUserId());
		logger.info("Failure message is : " + request.getErrorMessage());
		logger.info("Stack Trace on Client is : " + request.getStackTrace());
		return true;
	}
}
