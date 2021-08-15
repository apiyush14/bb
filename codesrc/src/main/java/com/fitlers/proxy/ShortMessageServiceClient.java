package com.fitlers.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fitlers.model.request.ShortMessageServiceRequest;
import com.fitlers.model.response.ShortMessageServiceResponse;

@FeignClient(name = "SMSService", url = "https://www.fast2sms.com")
public interface ShortMessageServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/dev/bulkV2")
	public ShortMessageServiceResponse sendShortMessage(@RequestHeader("authorization") String authorizationKey,
			ShortMessageServiceRequest request);
	
}
