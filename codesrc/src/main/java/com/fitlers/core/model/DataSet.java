package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataSet {
	
	
 private String dataSourceId;
 private Point[] point;
public String getDataSourceId() {
	return dataSourceId;
}
public Point[] getPoint() {
	return point;
}
public void setDataSourceId(String dataSourceId) {
	this.dataSourceId = dataSourceId;
}
public void setPoint(Point[] point) {
	this.point = point;
}

}
