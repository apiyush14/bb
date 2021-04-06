package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.EventResultDetails;

public class GetEventResultDetailsResponse {
	private List<EventResultDetails> eventResultDetails;
	private boolean isMoreContentAvailable;

	public List<EventResultDetails> getEventResultDetails() {
		return eventResultDetails;
	}

	public void setEventResultDetails(List<EventResultDetails> eventResultDetails) {
		this.eventResultDetails = eventResultDetails;
	}
	
	public boolean isMoreContentAvailable() {
		return isMoreContentAvailable;
	}

	public void setMoreContentAvailable(boolean isMoreContentAvailable) {
		this.isMoreContentAvailable = isMoreContentAvailable;
	}

}
