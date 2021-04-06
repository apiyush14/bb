package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.EventResultDetailsWithUserDetails;

public class GetEventResultDetailsForEventResponse {
	private List<EventResultDetailsWithUserDetails> eventResultDetailsWithUserDetails;
	private boolean isMoreContentAvailable;

	public List<EventResultDetailsWithUserDetails> getEventResultDetailsWithUserDetails() {
		return eventResultDetailsWithUserDetails;
	}

	public void setEventResultDetailsWithUserDetails(
			List<EventResultDetailsWithUserDetails> eventResultDetailsWithUserDetails) {
		this.eventResultDetailsWithUserDetails = eventResultDetailsWithUserDetails;
	}
	
	public boolean isMoreContentAvailable() {
		return isMoreContentAvailable;
	}

	public void setMoreContentAvailable(boolean isMoreContentAvailable) {
		this.isMoreContentAvailable = isMoreContentAvailable;
	}
}
