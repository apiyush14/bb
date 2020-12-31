package com.onehealth.model.response;

import java.util.List;

import com.onehealth.entities.EventResultDetails;

public class GetEventResultDetailsResponse {

	private List<EventResultDetails> eventResultDetails;

	public List<EventResultDetails> getEventResultDetails() {
		return eventResultDetails;
	}

	public void setEventResultDetails(List<EventResultDetails> eventResultDetails) {
		this.eventResultDetails = eventResultDetails;
	}

}
