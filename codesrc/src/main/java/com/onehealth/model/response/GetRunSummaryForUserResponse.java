package com.onehealth.model.response;

import com.onehealth.entities.RunSummary;

public class GetRunSummaryForUserResponse {
	private RunSummary runSummary;

	public RunSummary getRunSummary() {
		return runSummary;
	}

	public void setRunSummary(RunSummary runSummary) {
		this.runSummary = runSummary;
	}
}
