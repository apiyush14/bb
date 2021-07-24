package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.GoogleFitRestRequest;
import com.fitlers.processors.AddRunsRequestProcessor;
import com.fitlers.processors.GetGoogleFitDataRequestProcessor;

@RestController
public class GoogleFitDataService extends BaseService {


	@Autowired
	AddRunsRequestProcessor addRunsRequestProcessor;

	@Autowired
	GetGoogleFitDataRequestProcessor getGoogleFitDataRequestProcessor;


	

	@PostMapping (path = "google-fit/save-data/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRunsForUser(@PathVariable String userId) {
		
		GoogleFitRestRequest request = new GoogleFitRestRequest();
		request.setUserId(userId);
		getGoogleFitDataRequestProcessor.setRequest(request);
		return execute(getGoogleFitDataRequestProcessor);
	}


}
