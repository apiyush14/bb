package com.onehealth.processors;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.UserAuthenticationDetails;
import com.onehealth.entities.UserDetails;
import com.onehealth.model.request.GetOTPForMSISDNRequest;
import com.onehealth.repo.UserAuthenticationDetailsRepository;
import com.onehealth.repo.UserDetailsRepository;

@Component
public class GetOTPForMSISDNRequestProcessor extends RequestProcessor<GetOTPForMSISDNRequest, Boolean> {

	@Autowired
	UserAuthenticationDetailsRepository userAuthenticationDetailsRepo;
	
	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Override
	public Boolean doProcessing(GetOTPForMSISDNRequest request) throws Exception {
		generateOTPForMSISDN(request);
		return true;
	}

	// To be replaced with the real call to SMS Gateway Service
	private void generateOTPForMSISDN(GetOTPForMSISDNRequest request) {
		int otpCode = (int) Math.abs(((Math.random() * (0001 - 9999)) + 0001));
		
		UserAuthenticationDetails userAuthenticationDetailsQueryObj = new UserAuthenticationDetails();
		userAuthenticationDetailsQueryObj.setUserMSISDN(request.getContactNumber());
		Example<UserAuthenticationDetails> userAuthenticationDetailsQueryExample = Example
				.of(userAuthenticationDetailsQueryObj);
		Optional<UserAuthenticationDetails> userAuthenticationDetailsOptional = userAuthenticationDetailsRepo
				.findOne(userAuthenticationDetailsQueryExample);
		
		if(userAuthenticationDetailsOptional.isPresent()) {
			UserAuthenticationDetails userAuthenticationDetails=userAuthenticationDetailsOptional.get();
			userAuthenticationDetails.setUserGeneratedOTP(String.valueOf(otpCode));
			userAuthenticationDetailsRepo.save(userAuthenticationDetails);
		}
		else {
			UserDetails userDetails=createNewUser();
			
			UserAuthenticationDetails userAuthenticationDetails= new UserAuthenticationDetails();
			userAuthenticationDetails.setUserId(userDetails.getUserId());
			userAuthenticationDetails.setUserMSISDN(request.getContactNumber());
			userAuthenticationDetails.setUserGeneratedOTP(String.valueOf(otpCode));
			userAuthenticationDetails.setUserDetails(userDetails);
			userAuthenticationDetailsRepo.save(userAuthenticationDetails);
		}
		System.out.println("===========Generated OTP For MSISDN===================" + otpCode);
	}

	@Transactional
	private UserDetails createNewUser() {
		UserDetails userDetails= new UserDetails();
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetailsRepo.save(userDetails);
		return userDetails;
	}

}
