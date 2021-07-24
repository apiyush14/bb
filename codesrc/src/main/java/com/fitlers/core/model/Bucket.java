package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bucket {

	
	private String startTimeMillis;
	private String endTimeMillis;
	private DataSet[] dataset;
	public String getStartTimeMillis() {
		return startTimeMillis;
	}
	public String getEndTimeMillis() {
		return endTimeMillis;
	}
	public DataSet[] getDataset() {
		return dataset;
	}
	public void setStartTimeMillis(String startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}
	public void setEndTimeMillis(String endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}
	public void setDataset(DataSet[] dataset) {
		this.dataset = dataset;
	}
	
}
