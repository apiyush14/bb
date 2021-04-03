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
@Table(name = "EVENT_RESULT_DETAILS")
@IdClass(EventResultDetailsId.class)
public class EventResultDetails {

	@Id
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "RUN_ID")
	private Long runId;

	@Column(name = "RUN_TOTAL_TIME")
	private String runTotalTime;

	@Column(name = "USER_RANK")
	private Long userRank;

	@Column(name = "PARTITION_KEY")
	private String partitionKey;

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

	@JsonIgnore
	@ManyToOne
	@MapsId("eventId")
	@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
	private EventDetails eventDetails;

	/*
	 * @JsonIgnore
	 * 
	 * @OneToOne
	 * 
	 * @JoinColumns({
	 * 
	 * @JoinColumn(name = "RUN_ID", referencedColumnName = "RUN_ID"),
	 * 
	 * @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }) private
	 * RunDetails rundetails;
	 */

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

	public String getRunTotalTime() {
		return runTotalTime;
	}

	public void setRunTotalTime(String runTotalTime) {
		this.runTotalTime = runTotalTime;
	}

	public Long getUserRank() {
		return userRank;
	}

	public void setUserRank(Long userRank) {
		this.userRank = userRank;
	}

	public String getPartitionKey() {
		return partitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
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

}
