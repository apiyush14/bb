package com.onehealth.entities.event.registration;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="EVENT_REGISTRATION_DETAILS")
@IdClass(EventRegistrationDetailsId.class)
public class EventRegistrationDetails {

	 @Id
	 @Column(name="EVENT_ID")
	 private Long eventId;
	 
	 @Id
	 @Column(name="USER_ID")
	 private String userId;
	 
	 @CreationTimestamp
	 @Column(name="CREATED_DATE")
	 @Temporal(value=TemporalType.TIMESTAMP)
	 private Date createdDate;
	 
	 @UpdateTimestamp
	 @Column(name="UPDATED_DATE")
	 @Temporal(value=TemporalType.TIMESTAMP)
	 private Date updatedDate;

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
	
}
