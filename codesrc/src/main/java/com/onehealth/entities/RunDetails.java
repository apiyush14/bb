package com.onehealth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RUN_DETAILS")
public class RunDetails {

 @Column(name="RUN_ID")
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int runId;
	
 @Column(name="USER_ID")
 private String userId;
 
 @Column(name="PARTITION_KEY")
 private String partitionKey;
 
 @Column(name="RUN_TOTAL_TIME")
 private String runTotalTime;
 
 @Column(name="RUN_DISTANCE")
 private String runDistance;
 
 @Column(name="RUN_PACE")
 private String runPace;
 
 @Column(name="RUN_CALORIES_BURNT")
 private String runCaloriesBurnt;
 
 @Column(name="RUN_CREDITS")
 private String runCredits;
 
 @Column(name="RUN_DATE")
 private String runDate;
 
 @Column(name="RUN_DAY")
 private String runDay;
 
 @Column(name="RUN_PATH",columnDefinition="TEXT", length=65535)
 private String runPath;
 
 @Column(name="RUN_TRACK_SNAP_URL")
 private String runTrackSnapUrl;

public int getRunId() {
	return runId;
}

public void setRunId(int runId) {
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

public String getRunDistance() {
	return runDistance;
}

public void setRunDistance(String runDistance) {
	this.runDistance = runDistance;
}

public String getRunPace() {
	return runPace;
}

public void setRunPace(String runPace) {
	this.runPace = runPace;
}

public String getRunCaloriesBurnt() {
	return runCaloriesBurnt;
}

public void setRunCaloriesBurnt(String runCaloriesBurnt) {
	this.runCaloriesBurnt = runCaloriesBurnt;
}

public String getRunCredits() {
	return runCredits;
}

public void setRunCredits(String runCredits) {
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

}
