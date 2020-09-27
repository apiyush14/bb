package com.onehealth.model;

import java.util.List;

import com.onehealth.entities.RunDetails;

public class AddRunDetailsRequest {
 private List<RunDetails> runDetailsList;

public List<RunDetails> getRunDetailsList() {
	return runDetailsList;
}

public void setRunDetailsList(List<RunDetails> runDetailsList) {
	this.runDetailsList = runDetailsList;
}
}
