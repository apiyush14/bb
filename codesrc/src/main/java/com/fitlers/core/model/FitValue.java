package com.fitlers.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FitValue {
	
private int intVal;
private float fpVal;
private MapVal[] mapVal;
public int getIntVal() {
	return intVal;
}
public float getFpVal() {
	return fpVal;
}
public MapVal[] getMapVal() {
	return mapVal;
}
public void setIntVal(int intVal) {
	this.intVal = intVal;
}
public void setFpVal(float fpVal) {
	this.fpVal = fpVal;
}
public void setMapVal(MapVal[] mapVal) {
	this.mapVal = mapVal;
}

}
