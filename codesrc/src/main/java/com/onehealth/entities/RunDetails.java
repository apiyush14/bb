package com.onehealth.entities;

import java.sql.Date;

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
@Table(name="RUN_DETAILS")
@IdClass(RunDetailsId.class)
public class RunDetails {

 @Column(name="RUN_ID")
 @Id
 private Integer runId;
	
 @Column(name="USER_ID")
 @Id
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
 private Date createdDate;
 
 @UpdateTimestamp
 @Column(name="UPDATED_DATE")
 private Date updatedDate;

public Integer getRunId() {
	return runId;
}

public void setRunId(Integer runId) {
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
	result = prime * result + (userId +"_"+runId).hashCode();
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
	if (runId != other.runId)
		return false;
	if (userId == null) {
		if (other.userId != null)
			return false;
	} else if (!(userId+"_"+runId).equals((other.userId+"_"+runId)))
		return false;
	return true;
}

}
