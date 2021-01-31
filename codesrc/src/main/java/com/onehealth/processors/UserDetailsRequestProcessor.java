package com.onehealth.processors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.UserDetails;
import com.onehealth.model.request.UserDetailsRequest;
import com.onehealth.repo.UserDetailsRepository;

@Component
public class UserDetailsRequestProcessor extends RequestProcessor<UserDetailsRequest, Boolean> {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	private static final Logger LOG = Logger.getLogger(UserDetailsRequestProcessor.class);

	@Override
	public Boolean doProcessing(UserDetailsRequest request) {
		UserDetails user = request.getUserDetails();
		LOG.debug("Saving user :"+user.getUserFirstName()+" "+user.getUserLastName());
		userDetailsRepo.save(request.getUserDetails());
		LOG.debug("User saved successfully.");
		return true;
	}

}
