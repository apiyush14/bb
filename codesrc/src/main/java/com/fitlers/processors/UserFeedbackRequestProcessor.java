package com.fitlers.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.UserFeedbackRequest;
import com.fitlers.repo.UserFeedbackRepository;

@Component
public class UserFeedbackRequestProcessor extends RequestProcessor<UserFeedbackRequest, Boolean> {

	@Autowired
	private UserFeedbackRepository userFeedbackRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(UserFeedbackRequestProcessor.class);

	@Override
	public Boolean doProcessing(UserFeedbackRequest request) throws Exception {
		logger.info("UserFeedbackRequestProcessor doProcessing Started for User Id " + request.getUserFeedBack().getUserId());
		userFeedbackRepository.save(request.getUserFeedBack());
		logger.info("UserFeedbackRequestProcessor doProcessing Started for User Id " + request.getUserFeedBack().getUserId());
		return true;
	}
}
