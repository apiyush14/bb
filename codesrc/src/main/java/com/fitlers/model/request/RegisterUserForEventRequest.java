package com.fitlers.model.request;

import com.fitlers.entities.EventRegistrationDetails;

public class RegisterUserForEventRequest {

	private EventRegistrationDetails eventRegistrationDetails;

	public EventRegistrationDetails getEventRegistrationDetails() {
		return eventRegistrationDetails;
	}

	public void setEventRegistrationDetails(EventRegistrationDetails eventRegistrationDetails) {
		this.eventRegistrationDetails = eventRegistrationDetails;
	}

}
