package com.fitlers.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.UserDetailsRequest;
import com.fitlers.repo.UserDetailsRepository;

@Component
public class UserDetailsRequestProcessor extends RequestProcessor<UserDetailsRequest, Boolean> {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	public static final Logger logger = LoggerFactory.getLogger(UserDetailsRequestProcessor.class);

	@Override
	public Boolean doProcessing(UserDetailsRequest request) throws Exception {
		logger.info("UserDetailsRequestProcessor doProcessing Started for User Id " + request.getUserDetails().getUserId());
		userDetailsRepo.save(request.getUserDetails());
		logger.info("UserDetailsRequestProcessor doProcessing Completed for User Id " + request.getUserDetails().getUserId());
		return true;
	}
}
