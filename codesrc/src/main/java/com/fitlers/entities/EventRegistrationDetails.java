package com.fitlers.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EVENT_REGISTRATION_DETAILS")
@IdClass(EventRegistrationDetailsId.class)
public class EventRegistrationDetails {

	@Id
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "RUN_ID")
	private Long runId;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "UPDATED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedDate;

	@JsonIgnore
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private UserDetails userDetails;

	@ManyToOne
	@MapsId("eventId")
	@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
	private EventDetails eventDetails;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public EventDetails getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(EventDetails eventDetails) {
		this.eventDetails = eventDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (userId + "_" + this.getEventId()).hashCode();
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
		EventRegistrationDetails other = (EventRegistrationDetails) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!(userId + "_" + this.getEventId()).equals((other.userId + "_" + other.getEventId())))
			return false;
		return true;
	}

}
