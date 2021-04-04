package com.fitlers.model.request;

import com.fitlers.core.model.BaseRequestProcessorInput;
import com.fitlers.entities.UserFeedback;

public class UserFeedbackRequest extends BaseRequestProcessorInput {
	private UserFeedback userFeedBack;

	public UserFeedback getUserFeedBack() {
		return userFeedBack;
	}

	public void setUserFeedBack(UserFeedback userFeedBack) {
		this.userFeedBack = userFeedBack;
	}
}
