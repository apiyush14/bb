package com.onehealth.model.event.registration;

import com.onehealth.entities.event.registration.EventRegistrationDetails;

public class RegisterUserForEventRequest {

private EventRegistrationDetails eventRegistrationDetails;

public EventRegistrationDetails getEventRegistrationDetails() {
	return eventRegistrationDetails;
}

public void setEventRegistrationDetails(EventRegistrationDetails eventRegistrationDetails) {
	this.eventRegistrationDetails = eventRegistrationDetails;
}

}
