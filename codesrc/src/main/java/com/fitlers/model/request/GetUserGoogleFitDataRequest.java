package com.fitlers.model.request;

public class GetUserGoogleFitDataRequest {
private AggregateBy aggregateBy[];
private BucketByTime bucketByTime;
private long startTimeMillis;
private long endTimeMillis;
public AggregateBy[] getAggregateBy() {
	return aggregateBy;
}
public void setAggregateBy(AggregateBy[] aggregateBy) {
	this.aggregateBy = aggregateBy;
}
public BucketByTime getBucketByTime() {
	return bucketByTime;
}
public void setBucketByTime(BucketByTime bucketByTime) {
	this.bucketByTime = bucketByTime;
}
public long getStartTimeMillis() {
	return startTimeMillis;
}
public void setStartTimeMillis(long startTimeMillis) {
	this.startTimeMillis = startTimeMillis;
}
public long getEndTimeMillis() {
	return endTimeMillis;
}
public void setEndTimeMillis(long endTimeMillis) {
	this.endTimeMillis = endTimeMillis;
}
}
