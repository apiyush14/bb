package com.onehealth.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="EVENT_DETAILS")
public class EventDetails {
 
 @Id
 @Column(name="EVENT_ID")
 @GeneratedValue
 private Long eventId;
 
 @Column(name="EVENT_ORGANIZER_FIRST_NAME")
 private String eventOrganizerFirstName;
 
 @Column(name="EVENT_ORGANIZER_LAST_NAME")
 private String eventOrganizerLastName;
 
 @Column(name="EVENT_ORGANIZER_CONTACT_NUMBER")
 private String eventOrganizerContactNumber;
 
 @Column(name="EVENT_NAME")
 private String eventName;
 
 @Column(name="EVENT_DESCRIPTION")
 private String eventDescription;
 
 @Column(name="EVENT_START_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 //@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
 @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
 private Date eventStartDate;
 
 @Column(name="EVENT_END_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 //@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
 @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
 private Date eventEndDate;
 
 @Column(name="EVENT_DISPLAY_PIC")
 private String eventDisplayPic;
 
 @CreationTimestamp
 @Column(name="CREATED_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 private Date createdDate;
 
 @UpdateTimestamp
 @Column(name="UPDATED_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 private Date updatedDate;
 
 @OneToMany(mappedBy = "eventDetails")
 private Set<EventRegistrationDetails> eventRegistrationDetails;
 
 @OneToMany(mappedBy = "eventDetails")
 private Set<EventResultDetails> eventResultDetails;

public Long getEventId() {
	return eventId;
}

public void setEventId(Long eventId) {
	this.eventId = eventId;
}

public String getEventOrganizerFirstName() {
	return eventOrganizerFirstName;
}

public void setEventOrganizerFirstName(String eventOrganizerFirstName) {
	this.eventOrganizerFirstName = eventOrganizerFirstName;
}

public String getEventOrganizerLastName() {
	return eventOrganizerLastName;
}

public void setEventOrganizerLastName(String eventOrganizerLastName) {
	this.eventOrganizerLastName = eventOrganizerLastName;
}

public String getEventOrganizerContactNumber() {
	return eventOrganizerContactNumber;
}

public void setEventOrganizerContactNumber(String eventOrganizerContactNumber) {
	this.eventOrganizerContactNumber = eventOrganizerContactNumber;
}

public String getEventName() {
	return eventName;
}

public void setEventName(String eventName) {
	this.eventName = eventName;
}

public String getEventDescription() {
	return eventDescription;
}

public void setEventDescription(String eventDescription) {
	this.eventDescription = eventDescription;
}

public Date getEventStartDate() {
	return eventStartDate;
}

public void setEventStartDate(Date eventStartDate) {
	this.eventStartDate = eventStartDate;
}

public Date getEventEndDate() {
	return eventEndDate;
}

public void setEventEndDate(Date eventEndDate) {
	this.eventEndDate = eventEndDate;
}

public String getEventDisplayPic() {
	return eventDisplayPic;
}

public void setEventDisplayPic(String eventDisplayPic) {
	this.eventDisplayPic = eventDisplayPic;
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

public Set<EventRegistrationDetails> getEventRegistrationDetails() {
	return eventRegistrationDetails;
}

public void setEventRegistrationDetails(Set<EventRegistrationDetails> eventRegistrationDetails) {
	this.eventRegistrationDetails = eventRegistrationDetails;
}

public Set<EventResultDetails> getEventResultDetails() {
	return eventResultDetails;
}

public void setEventResultDetails(Set<EventResultDetails> eventResultDetails) {
	this.eventResultDetails = eventResultDetails;
}

}
