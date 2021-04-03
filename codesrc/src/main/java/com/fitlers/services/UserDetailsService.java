package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.UserDetailsRequest;
import com.fitlers.processors.GetUserDetailsRequestProcessor;
import com.fitlers.processors.UserDetailsRequestProcessor;

@RestController
public class UserDetailsService extends BaseService {

	@Autowired
	private UserDetailsRequestProcessor userDetailsRequestProcessor;

	@Autowired
	private GetUserDetailsRequestProcessor getUserDetailsRequestProcessor;

	@PutMapping(path = "user/updateDetails/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUserDetails(@RequestBody UserDetailsRequest userDetailsRequest,
			@PathVariable String userId) {
		userDetailsRequest.getUserDetails().setUserId(userId);
		userDetailsRequestProcessor.setRequest(userDetailsRequest);
		return execute(userDetailsRequestProcessor);
	}

	@GetMapping(path = "user/getDetails/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUserDetails(@PathVariable String userId) {
		getUserDetailsRequestProcessor.setRequest(userId);
		return execute(getUserDetailsRequestProcessor);
	}

}
