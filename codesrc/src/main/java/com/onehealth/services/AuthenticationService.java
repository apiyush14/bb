package com.onehealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.service.BaseService;
import com.onehealth.model.request.GetOTPForMSISDNRequest;
import com.onehealth.model.request.ValidateOTPAndGenerateSecretRequest;
import com.onehealth.processors.GetOTPForMSISDNRequestProcessor;
import com.onehealth.processors.ValidateOTPAndGenerateSecretRequestProcessor;

@RestController
public class AuthenticationService extends BaseService {

	@Autowired
	GetOTPForMSISDNRequestProcessor getOTPForMSISDNRequestProcessor;

	@Autowired
	ValidateOTPAndGenerateSecretRequestProcessor validateOTPAndGenerateSecretRequestProcessor;

	// TODO MSISDN needs to be encrypted
	@GetMapping(path = "auth/getOTP/{msisdn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRunsForUser(@PathVariable String msisdn) {
		GetOTPForMSISDNRequest request = new GetOTPForMSISDNRequest();
		request.setContactNumber(msisdn);
		getOTPForMSISDNRequestProcessor.setRequest(request);
		return execute(getOTPForMSISDNRequestProcessor);
	}

	@GetMapping(path = "auth/validateOTP/{msisdn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateAndGenerateSecret(@PathVariable String msisdn,
			@RequestParam(required = true, name = "otpCode") String otpCode) {
		ValidateOTPAndGenerateSecretRequest request = new ValidateOTPAndGenerateSecretRequest();
		request.setMsisdn(msisdn);
		request.setOtpCode(otpCode);
		validateOTPAndGenerateSecretRequestProcessor.setRequest(request);
		return execute(validateOTPAndGenerateSecretRequestProcessor);
	}
}
