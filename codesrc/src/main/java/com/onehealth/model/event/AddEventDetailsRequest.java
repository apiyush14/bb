package com.onehealth.model.event;

import com.onehealth.entities.event.EventDetails;

public class AddEventDetailsRequest {
 
 EventDetails eventDetails;

public EventDetails getEventDetails() {
	return eventDetails;
}

public void setEventDetails(EventDetails eventDetails) {
	this.eventDetails = eventDetails;
}
}
