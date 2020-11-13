package com.onehealth.entities;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="RUN_DETAILS")
@IdClass(RunDetailsId.class)
public class RunDetails {

 @Id
 @Column(name="RUN_ID")
 private Long runId;
	
 @Id
 @Column(name="USER_ID")
 private String userId;
 
 @Column(name="PARTITION_KEY")
 private String partitionKey;
 
 @Column(name="RUN_TOTAL_TIME")
 private String runTotalTime;
 
 @Column(name="RUN_DISTANCE")
 private Double runDistance;
 
 @Column(name="RUN_PACE")
 private Double runPace;
 
 @Column(name="RUN_CALORIES_BURNT")
 private Double runCaloriesBurnt;
 
 @Column(name="RUN_CREDITS")
 private Double runCredits;
 
 @Column(name="RUN_START_DATE_TIME")
 @Temporal(value=TemporalType.TIMESTAMP)
 @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
 private Date runStartDateTime;

 @Column(name="RUN_DATE")
 private String runDate;
 
 @Column(name="RUN_DAY")
 private String runDay;
 
 @Column(name="RUN_PATH",columnDefinition="TEXT", length=65535)
 private String runPath;
 
 @Column(name="RUN_TRACK_SNAP_URL")
 private String runTrackSnapUrl;

 @CreationTimestamp
 @Column(name="CREATED_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 private Date createdDate;
 
 @UpdateTimestamp
 @Column(name="UPDATED_DATE")
 @Temporal(value=TemporalType.TIMESTAMP)
 private Date updatedDate;

public Long getRunId() {
	return runId;
}

public void setRunId(Long runId) {
	this.runId = runId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getPartitionKey() {
	return partitionKey;
}

public void setPartitionKey(String partitionKey) {
	this.partitionKey = partitionKey;
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

public Double getRunPace() {
	return runPace;
}

public void setRunPace(Double runPace) {
	this.runPace = runPace;
}

public Double getRunCaloriesBurnt() {
	return runCaloriesBurnt;
}

public void setRunCaloriesBurnt(Double runCaloriesBurnt) {
	this.runCaloriesBurnt = runCaloriesBurnt;
}

public Double getRunCredits() {
	return runCredits;
}

public void setRunCredits(Double runCredits) {
	this.runCredits = runCredits;
}

public Date getRunStartDateTime() {
	return runStartDateTime;
}

public void setRunStartDateTime(Date runStartDateTime) {
	this.runStartDateTime = runStartDateTime;
}

public String getRunDate() {
	return runDate;
}

public void setRunDate(String runDate) {
	this.runDate = runDate;
}

public String getRunDay() {
	return runDay;
}

public void setRunDay(String runDay) {
	this.runDay = runDay;
}

public String getRunPath() {
	return runPath;
}

public void setRunPath(String runPath) {
	this.runPath = runPath;
}

public String getRunTrackSnapUrl() {
	return runTrackSnapUrl;
}

public void setRunTrackSnapUrl(String runTrackSnapUrl) {
	this.runTrackSnapUrl = runTrackSnapUrl;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (userId +"_"+this.getRunId()).hashCode();
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
	RunDetails other = (RunDetails) obj;
	if (userId == null) {
		if (other.userId != null)
			return false;
	} else if (!(userId+"_"+this.getRunId()).equals((other.userId+"_"+other.getRunId())))
		return false;
	return true;
}

}
