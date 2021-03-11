package com.onehealth.processors;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.UserDetails;
import com.onehealth.model.response.GetUserDetailsResponse;
import com.onehealth.repo.UserDetailsRepository;

@Component
public class GetUserDetailsRequestProcessor extends RequestProcessor<String, GetUserDetailsResponse> {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Override
	public GetUserDetailsResponse doProcessing(String userId) throws Exception {
		GetUserDetailsResponse response = new GetUserDetailsResponse();
		Optional<UserDetails> userDetailsOptional = userDetailsRepo.findById(userId);
		if (userDetailsOptional.isPresent()) {
			response.setUserDetails(userDetailsOptional.get());
		}
		return response;
	}

}
