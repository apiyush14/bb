package com.onehealth.processors;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.UserAuthenticationDetails;
import com.onehealth.model.request.ValidateOTPAndGenerateSecretRequest;
import com.onehealth.model.response.ValidateOTPAndGenerateSecretResponse;
import com.onehealth.repo.UserAuthenticationDetailsRepository;
import com.onehealth.utils.AuthenticationUtils;

@Component
public class ValidateOTPAndGenerateSecretRequestProcessor
		extends RequestProcessor<ValidateOTPAndGenerateSecretRequest, ValidateOTPAndGenerateSecretResponse> {

	@Autowired
	UserAuthenticationDetailsRepository userAuthenticationDetailsRepository;

	@Override
	public ValidateOTPAndGenerateSecretResponse doProcessing(ValidateOTPAndGenerateSecretRequest request)
			throws Exception {
		ValidateOTPAndGenerateSecretResponse response = new ValidateOTPAndGenerateSecretResponse();
		
		UserAuthenticationDetails userAuthenticationDetailsQueryObj = new UserAuthenticationDetails();
		userAuthenticationDetailsQueryObj.setUserMSISDN(request.getMsisdn());
		Example<UserAuthenticationDetails> userAuthenticationDetailsQueryExample = Example
				.of(userAuthenticationDetailsQueryObj);
		Optional<UserAuthenticationDetails> userAuthenticationDetailsOptional = userAuthenticationDetailsRepository
				.findOne(userAuthenticationDetailsQueryExample);
		
		if (request.getOtpCode().equals(userAuthenticationDetailsOptional.get().getUserGeneratedOTP())) {
			UserAuthenticationDetails userAuthenticationDetails=userAuthenticationDetailsOptional.get();
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			byte[] userSecretKey=messageDigest.digest((request.getMsisdn()+System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
			
			userAuthenticationDetails.setUserSecretKey(AuthenticationUtils.bytesToHex(userSecretKey));
			userAuthenticationDetailsRepository.save(userAuthenticationDetails);
			response.setUserId(userAuthenticationDetails.getUserId());
			response.setSecret(AuthenticationUtils.bytesToHex(userSecretKey));
			response.setIsValid(true);
		} else {
			response.setIsValid(false);
		}
		System.out.println("======================="+response.getSecret()+"=====================");
		return response;
	}

}
