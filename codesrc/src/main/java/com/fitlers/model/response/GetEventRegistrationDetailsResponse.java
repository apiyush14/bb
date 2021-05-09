package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.EventRegistrationDetails;

public class GetEventRegistrationDetailsResponse {
	private List<EventRegistrationDetails> eventRegistrationDetails;
	private boolean isMoreContentAvailable;

	public List<EventRegistrationDetails> getEventRegistrationDetails() {
		return eventRegistrationDetails;
	}

	public void setEventRegistrationDetails(List<EventRegistrationDetails> eventRegistrationDetails) {
		this.eventRegistrationDetails = eventRegistrationDetails;
	}

	public boolean isMoreContentAvailable() {
		return isMoreContentAvailable;
	}

	public void setMoreContentAvailable(boolean isMoreContentAvailable) {
		this.isMoreContentAvailable = isMoreContentAvailable;
	}

}
