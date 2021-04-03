package com.fitlers.model.response;

import java.util.List;

import com.fitlers.entities.EventDetails;

public class GetEventDetailsResponse {
	List<EventDetails> eventDetails;

	public List<EventDetails> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(List<EventDetails> eventDetails) {
		this.eventDetails = eventDetails;
	}

}
