package com.onehealth.model.response;

import java.util.List;

import com.onehealth.entities.EventDetails;

public class GetEventDetailsResponse {
	List<EventDetails> eventDetails;

	public List<EventDetails> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(List<EventDetails> eventDetails) {
		this.eventDetails = eventDetails;
	}

}
