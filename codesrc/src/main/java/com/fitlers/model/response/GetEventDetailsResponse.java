package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.EventDetails;

public class GetEventDetailsResponse {
	private List<EventDetails> eventDetails;
	private boolean isMoreContentAvailable;

	public List<EventDetails> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(List<EventDetails> eventDetails) {
		this.eventDetails = eventDetails;
	}

	public boolean isMoreContentAvailable() {
		return isMoreContentAvailable;
	}

	public void setMoreContentAvailable(boolean isMoreContentAvailable) {
		this.isMoreContentAvailable = isMoreContentAvailable;
	}
}
