package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.UserFeedbackRequest;
import com.fitlers.processors.UserFeedbackRequestProcessor;

@RestController
public class UserFeedbackService extends BaseService {

	@Autowired
	private UserFeedbackRequestProcessor userFeedbackRequestProcessor;

	@PostMapping(path = "user/feedback/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> userFeedback(@PathVariable String userId,
			@RequestBody UserFeedbackRequest userFeedbackRequest) {
		UserFeedbackRequest request = new UserFeedbackRequest();
		request.setUserFeedBack(userFeedbackRequest.getUserFeedBack());
		request.getUserFeedBack().setUserId(userId);
		userFeedbackRequestProcessor.setRequest(request);
		return execute(userFeedbackRequestProcessor);
	}
}
