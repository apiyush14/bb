package com.fitlers.processors;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.UserDetails;
import com.fitlers.model.response.GetUserDetailsResponse;
import com.fitlers.repo.UserDetailsRepository;

@Component
public class GetUserDetailsRequestProcessor extends RequestProcessor<String, GetUserDetailsResponse> {

	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	public static final Logger logger = LoggerFactory.getLogger(GetUserDetailsRequestProcessor.class);

	@Override
	public GetUserDetailsResponse doProcessing(String userId) throws Exception {
		logger.info("GetUserDetailsRequestProcessor doProcessing Started for User Id " + userId);
		GetUserDetailsResponse response = new GetUserDetailsResponse();
		Optional<UserDetails> userDetailsOptional = userDetailsRepo.findById(userId);
		if (userDetailsOptional.isPresent()) {
			response.setUserDetails(userDetailsOptional.get());
		}
		logger.info("GetUserDetailsRequestProcessor doProcessing Started for User Id " + userId);
		return response;
	}

}
