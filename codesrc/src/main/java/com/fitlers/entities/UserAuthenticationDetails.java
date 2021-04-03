package com.fitlers.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_AUTHENTICATION_DETAILS")
public class UserAuthenticationDetails {

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_MSISDN")
	private String userMSISDN;

	@Column(name = "USER_GENERATED_OTP")
	private String userGeneratedOTP;

	@Column(name = "USER_SECRET_KEY")
	private String userSecretKey;

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId("userId")
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private UserDetails userDetails;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserMSISDN() {
		return userMSISDN;
	}

	public void setUserMSISDN(String userMSISDN) {
		this.userMSISDN = userMSISDN;
	}

	public String getUserGeneratedOTP() {
		return userGeneratedOTP;
	}

	public void setUserGeneratedOTP(String userGeneratedOTP) {
		this.userGeneratedOTP = userGeneratedOTP;
	}

	public String getUserSecretKey() {
		return userSecretKey;
	}

	public void setUserSecretKey(String userSecretKey) {
		this.userSecretKey = userSecretKey;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
