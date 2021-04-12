package com.fitlers.processors;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fitlers.constants.EncryptionKeys;
import com.fitlers.core.encryption.Encrypter;
import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.UserAuthenticationDetails;
import com.fitlers.entities.UserDetails;
import com.fitlers.model.request.GetOTPForMSISDNRequest;
import com.fitlers.model.request.ShortMessageServiceRequest;
import com.fitlers.model.response.GetOTPForMSISDNResponse;
import com.fitlers.proxy.ServiceProxy;
import com.fitlers.repo.UserAuthenticationDetailsRepository;
import com.fitlers.repo.UserDetailsRepository;

@Component
public class GetOTPForMSISDNRequestProcessor extends RequestProcessor<GetOTPForMSISDNRequest, GetOTPForMSISDNResponse> {

	@Autowired
	UserAuthenticationDetailsRepository userAuthenticationDetailsRepo;

	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Autowired
	ServiceProxy serviceProxy;

	@Autowired
	private Encrypter encrypter;

	@Autowired
	Environment env;

	public static final Logger logger = LoggerFactory.getLogger(GetOTPForMSISDNRequestProcessor.class);

	@Override
	public GetOTPForMSISDNResponse doProcessing(GetOTPForMSISDNRequest request) throws Exception {
		logger.info("GetOTPForMSISDNRequestProcessor doProcessing Started for Contact Number "
				+ request.getContactNumber());
		return generateOTPForMSISDN(request);
	}

	private GetOTPForMSISDNResponse generateOTPForMSISDN(GetOTPForMSISDNRequest request) {

		GetOTPForMSISDNResponse response = new GetOTPForMSISDNResponse();

		int otpCode = (int) Math.abs(((Math.random() * (1111 - 9999)) + 1111));
		boolean status = callSMSGateway(request, otpCode);
		if (status) {
			updateOrCreateUserAuthenticationDetails(request, response, otpCode);
		} else {
			response.setStatus(false);
		}
		return response;
	}

	private void updateOrCreateUserAuthenticationDetails(GetOTPForMSISDNRequest request,
			GetOTPForMSISDNResponse response, int otpCode) {
		String encryptedMSISDN = encrypter.encrypt(EncryptionKeys.ENCRYPTION_KEY_MSISDN, request.getContactNumber());
		UserAuthenticationDetails userAuthenticationDetailsQueryObj = new UserAuthenticationDetails();
		// Lookup to be done with encrypted value
		userAuthenticationDetailsQueryObj.setUserMSISDN(encryptedMSISDN);
		Example<UserAuthenticationDetails> userAuthenticationDetailsQueryExample = Example
				.of(userAuthenticationDetailsQueryObj);
		Optional<UserAuthenticationDetails> userAuthenticationDetailsOptional = userAuthenticationDetailsRepo
				.findOne(userAuthenticationDetailsQueryExample);

		if (userAuthenticationDetailsOptional.isPresent()) {
			UserAuthenticationDetails userAuthenticationDetails = userAuthenticationDetailsOptional.get();
			if (StringUtils.isEmpty(userAuthenticationDetails.getUserSecretKey())) {
				response.setNewUser(true);
			}
			userAuthenticationDetails.setUserGeneratedOTP(String.valueOf(otpCode));
			userAuthenticationDetailsRepo.save(userAuthenticationDetails);
		} else {
			UserDetails userDetails = createNewUser();

			UserAuthenticationDetails userAuthenticationDetails = new UserAuthenticationDetails();
			userAuthenticationDetails.setUserId(userDetails.getUserId());
			userAuthenticationDetails.setUserMSISDN(request.getContactNumber());
			userAuthenticationDetails.setUserGeneratedOTP(String.valueOf(otpCode));
			userAuthenticationDetails.setUserDetails(userDetails);
			userAuthenticationDetailsRepo.save(userAuthenticationDetails);
			response.setNewUser(true);
		}
		System.out.println("===========Generated OTP For MSISDN===================" + otpCode);
		response.setStatus(true);
		logger.info("GetOTPForMSISDNRequestProcessor doProcessing Completed for Contact Number "
				+ request.getContactNumber());
	}

	private boolean callSMSGateway(GetOTPForMSISDNRequest request, int otpCode) {
		if (env.acceptsProfiles(Profiles.of("prod"))) {
			ShortMessageServiceRequest shortMessageServiceRequest = new ShortMessageServiceRequest();
			shortMessageServiceRequest.setMessage("Your OTP is : " + otpCode);
			shortMessageServiceRequest.setNumbers(request.getContactNumber());
			shortMessageServiceRequest.setRoute("v3");
			shortMessageServiceRequest.setSender_id("TXTIND");
			boolean isMessageSent = serviceProxy.sendShortMessage(shortMessageServiceRequest);
			logger.info("GetOTPForMSISDNRequestProcessor doProcessing Message Successfully sent");
			return isMessageSent;
		}
		return true;
	}

	private UserDetails createNewUser() {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetailsRepo.save(userDetails);
		return userDetails;
	}

}
