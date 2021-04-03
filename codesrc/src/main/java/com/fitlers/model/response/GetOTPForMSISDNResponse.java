package com.fitlers.model.response;

public class GetOTPForMSISDNResponse {

	private boolean status;
	private boolean isNewUser;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isNewUser() {
		return isNewUser;
	}

	public void setNewUser(boolean isNewUser) {
		this.isNewUser = isNewUser;
	}

}
