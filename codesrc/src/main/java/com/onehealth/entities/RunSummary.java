package com.onehealth.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="RUN_SUMMARY")
public class RunSummary {
 
 @Id
 @Column(name="USER_ID")
 public String userId;

@Column(name="PARTITION_KEY")
 public String partitionKey;
 
 @Column(name="TOTAL_RUNS")
 public Integer totalRuns;
 
 @Column(name="TOTAL_DISTANCE")
 public Double totalDistance;
 
 @Column(name="TOTAL_CREDITS")
 public Double totalCredits;
 
 @Column(name="AVERAGE_PACE")
 public Double averagePace;
 
 @Column(name="AVERAGE_DISTANCE")
 public Double averageDistance;
 
 @Column(name="AVERAGE_CALORIES_BURNT")
 public Double averageCaloriesBurnt;

 @CreationTimestamp
 @Column(name="CREATED_DATE")
 private Date createdDate;
 
 @UpdateTimestamp
 @Column(name="UPDATED_DATE")
 private Date updatedDate;
 
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

public Integer getTotalRuns() {
	return totalRuns;
}

public void setTotalRuns(Integer totalRuns) {
	this.totalRuns = totalRuns;
}

public Double getTotalDistance() {
	return totalDistance;
}

public void setTotalDistance(Double totalDistance) {
	this.totalDistance = totalDistance;
}

public Double getTotalCredits() {
	return totalCredits;
}

public void setTotalCredits(Double totalCredits) {
	this.totalCredits = totalCredits;
}

public Double getAveragePace() {
	return averagePace;
}

public void setAveragePace(Double averagePace) {
	this.averagePace = averagePace;
}

public Double getAverageDistance() {
	return averageDistance;
}

public void setAverageDistance(Double averageDistance) {
	this.averageDistance = averageDistance;
}

public Double getAverageCaloriesBurnt() {
	return averageCaloriesBurnt;
}

public void setAverageCaloriesBurnt(Double averageCaloriesBurnt) {
	this.averageCaloriesBurnt = averageCaloriesBurnt;
}


 

}
