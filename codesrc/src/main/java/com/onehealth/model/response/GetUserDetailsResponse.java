package com.onehealth.model.response;

import com.onehealth.entities.UserDetails;

public class GetUserDetailsResponse {

	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
