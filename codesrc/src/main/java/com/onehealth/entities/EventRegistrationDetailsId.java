package com.onehealth.entities;

import java.io.Serializable;

public class EventRegistrationDetailsId implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long eventId;
	private String userId;

	public EventRegistrationDetailsId() {

	}

	public EventRegistrationDetailsId(Long eventId, String userId) {
		super();
		this.eventId = eventId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventRegistrationDetailsId other = (EventRegistrationDetailsId) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
