package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.RunDetails;

public class GetRunsForUserResponse {
	private List<RunDetails> runDetailsList;
	private boolean isMoreContentAvailable;

	public List<RunDetails> getRunDetailsList() {
		return runDetailsList;
	}

	public void setRunDetailsList(List<RunDetails> runDetailsList) {
		this.runDetailsList = runDetailsList;
	}

	public boolean isMoreContentAvailable() {
		return isMoreContentAvailable;
	}

	public void setMoreContentAvailable(boolean isMoreContentAvailable) {
		this.isMoreContentAvailable = isMoreContentAvailable;
	}
}
