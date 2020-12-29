package com.onehealth.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.UserDetailsRequest;
import com.onehealth.repo.UserDetailsRepository;

@Component
public class UserDetailsRequestProcessor extends RequestProcessor<UserDetailsRequest, Boolean> {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Override
	public Boolean doProcessing(UserDetailsRequest request) throws Exception {
		userDetailsRepo.save(request.getUserDetails());
		return true;
	}

}
