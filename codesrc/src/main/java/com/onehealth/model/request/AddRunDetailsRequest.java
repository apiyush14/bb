package com.onehealth.model.request;

import java.util.List;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.entities.RunDetails;

public class AddRunDetailsRequest extends BaseRequestProcessorInput {
	private List<RunDetails> runDetailsList;

	public List<RunDetails> getRunDetailsList() {
		return runDetailsList;
	}

	public void setRunDetailsList(List<RunDetails> runDetailsList) {
		this.runDetailsList = runDetailsList;
	}
}
