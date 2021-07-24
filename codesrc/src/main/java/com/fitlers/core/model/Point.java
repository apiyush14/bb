package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {
	
	
private String startTimeNanos;
private String endTimeNanos;
private String dataTypeName;
private String originDataSourceId;
private FitValue [] value;
public String getStartTimeNanos() {
	return startTimeNanos;
}
public String getEndTimeNanos() {
	return endTimeNanos;
}
public String getDataTypeName() {
	return dataTypeName;
}
public String getOriginDataSourceId() {
	return originDataSourceId;
}
public FitValue[] getValue() {
	return value;
}
public void setStartTimeNanos(String startTimeNanos) {
	this.startTimeNanos = startTimeNanos;
}
public void setEndTimeNanos(String endTimeNanos) {
	this.endTimeNanos = endTimeNanos;
}
public void setDataTypeName(String dataTypeName) {
	this.dataTypeName = dataTypeName;
}
public void setOriginDataSourceId(String originDataSourceId) {
	this.originDataSourceId = originDataSourceId;
}
public void setValue(FitValue[] value) {
	this.value = value;
}


}
