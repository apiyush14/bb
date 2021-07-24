package com.fitlers.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitlers.core.model.Bucket;
import com.fitlers.core.model.FitError;



public class GetUserGoogleFitDataResponse {

	
	
	private Bucket[] bucket;
	private FitError error;
	public void setBucket(Bucket[] bucket) {
		this.bucket = bucket;
	}

	public void setError(FitError error) {
		this.error = error;
	}


	
	public FitError getError() {
		return error;
	}

	public Bucket[] getBucket() {
		return bucket;
	}
  
}
