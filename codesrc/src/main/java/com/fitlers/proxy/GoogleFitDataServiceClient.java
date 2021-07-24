package com.fitlers.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fitlers.model.request.GetUserGoogleFitDataRequest;
import com.fitlers.model.request.ShortMessageServiceRequest;
import com.fitlers.model.response.GetUserGoogleFitDataResponse;
import com.fitlers.model.response.ShortMessageServiceResponse;


@FeignClient(name = "GoogleFitService", url = "https://www.googleapis.com")
public interface GoogleFitDataServiceClient {

	
	
	@RequestMapping(method = RequestMethod.POST, value = "/fitness/v1/users/me/dataset:aggregate")
	public GetUserGoogleFitDataResponse getGoogleFitData(@RequestHeader("Authorization") String authorizationKey,
			GetUserGoogleFitDataRequest request);
}
