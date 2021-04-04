package com.fitlers.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.UserFeedbackRequest;
import com.fitlers.repo.UserFeedbackRepository;

@Component
public class UserFeedbackRequestProcessor extends RequestProcessor<UserFeedbackRequest, Boolean> {

	@Autowired
	private UserFeedbackRepository userFeedbackRepository;

	@Override
	public Boolean doProcessing(UserFeedbackRequest request) throws Exception {
		userFeedbackRepository.save(request.getUserFeedBack());
		return true;
	}
}
