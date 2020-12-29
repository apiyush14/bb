package com.onehealth.model.request;

import com.onehealth.entities.EventRegistrationDetails;

public class RegisterUserForEventRequest {

private EventRegistrationDetails eventRegistrationDetails;

public EventRegistrationDetails getEventRegistrationDetails() {
	return eventRegistrationDetails;
}

public void setEventRegistrationDetails(EventRegistrationDetails eventRegistrationDetails) {
	this.eventRegistrationDetails = eventRegistrationDetails;
}

}
