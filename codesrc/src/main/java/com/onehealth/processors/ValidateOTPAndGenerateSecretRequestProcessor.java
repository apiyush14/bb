package com.onehealth.processors;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.onehealth.core.exceptions.NoUserFoundException;
import com.onehealth.core.exceptions.SecretKeyGenerationException;
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

	private static final Logger LOG = Logger.getLogger(ValidateOTPAndGenerateSecretRequestProcessor.class);

	@Override
	public ValidateOTPAndGenerateSecretResponse doProcessing(ValidateOTPAndGenerateSecretRequest request)
			 {
		LOG.debug("Validating User OTP :"+request.getOtpCode()+" for user with MSISDN :"+request.getMsisdn());
		ValidateOTPAndGenerateSecretResponse response = new ValidateOTPAndGenerateSecretResponse();
		
		UserAuthenticationDetails userAuthenticationDetailsQueryObj = new UserAuthenticationDetails();
		userAuthenticationDetailsQueryObj.setUserMSISDN(request.getMsisdn());
		Example<UserAuthenticationDetails> userAuthenticationDetailsQueryExample = Example
				.of(userAuthenticationDetailsQueryObj);
		Optional<UserAuthenticationDetails> userAuthenticationDetailsOptional = userAuthenticationDetailsRepository
				.findOne(userAuthenticationDetailsQueryExample);
		
		if(userAuthenticationDetailsOptional.isEmpty()) {
			LOG.error("No User found with MSISDN : "+request.getMsisdn());
			throw new NoUserFoundException();
		}
		if (request.getOtpCode().equals(userAuthenticationDetailsOptional.get().getUserGeneratedOTP())) {
		    LOG.debug("User authenticated for provided OTP, generating secret key.");
			UserAuthenticationDetails userAuthenticationDetails=userAuthenticationDetailsOptional.get();

			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			
			byte[] userSecretKey=messageDigest.digest((request.getMsisdn()+System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
			
			userAuthenticationDetails.setUserSecretKey(AuthenticationUtils.bytesToHex(userSecretKey));
			userAuthenticationDetailsRepository.save(userAuthenticationDetails);
			response.setUserId(userAuthenticationDetails.getUserId());
			response.setSecret(AuthenticationUtils.bytesToHex(userSecretKey));
			LOG.debug("User secret key generated successfully.");
			} catch (NoSuchAlgorithmException e) {
				LOG.error("Invalid algorithm used for generating secret key");
				throw new SecretKeyGenerationException();
			}
			response.setIsValid(true);
		} else {
			LOG.error("Authentication failed for user :" +request.getMsisdn()+" for OTP :"+request.getOtpCode());
			response.setIsValid(false);
		}
        // Do we need this in Logs
		LOG.debug("======================="+response.getSecret()+"=====================");
		return response;
	}

}
