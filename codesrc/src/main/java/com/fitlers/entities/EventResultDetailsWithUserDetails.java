package com.fitlers.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@IdClass(EventResultDetailsId.class)
@Subselect("select u.USER_ID as userId,e.EVENT_ID as eventId,u.USER_FIRST_NAME as userFirstName,u.USER_LAST_NAME as userLastName,e.USER_RANK as userRank,e.RUN_TOTAL_TIME as runTotalTime,e.RUN_DISTANCE as runDistance from EVENT_RESULT_DETAILS e, USER_DETAILS u where u.USER_ID=e.USER_ID")
public class EventResultDetailsWithUserDetails {

	@Id
	private String userId;

	@Id
	private Long eventId;

	private String userFirstName;

	private String userLastName;

	private Long userRank;
	
	private String runTotalTime;
	
	private Double runDistance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public Long getUserRank() {
		return userRank;
	}

	public void setUserRank(Long userRank) {
		this.userRank = userRank;
	}

	public String getRunTotalTime() {
		return runTotalTime;
	}

	public void setRunTotalTime(String runTotalTime) {
		this.runTotalTime = runTotalTime;
	}

	public Double getRunDistance() {
		return runDistance;
	}

	public void setRunDistance(Double runDistance) {
		this.runDistance = runDistance;
	}

}
