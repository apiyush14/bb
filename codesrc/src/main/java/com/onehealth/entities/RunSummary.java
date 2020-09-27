package com.onehealth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RUN_SUMMARY")
public class RunSummary {
 
 @Id
 @Column(name="USER_ID")
 public String userId;
 
 @Column(name="PARTITION_KEY")
 public String partitionKey;
 
 @Column(name="TOTAL_RUNS")
 public String totalRuns;
 
 @Column(name="TOTAL_DISTANCE")
 public String totalDistance;
 
 @Column(name="TOTAL_CREDITS")
 public String totalCredits;
 
 @Column(name="AVERAGE_PACE")
 public String averagePace;
 
 @Column(name="AVERAGE_DISTANCE")
 public String averageDistance;
 
 @Column(name="AVERAGE_CALORIES_BURNT")
 public String averageCaloriesBurnt;

}
