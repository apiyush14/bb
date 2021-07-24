package com.fitlers.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.model.FitError;
import com.fitlers.model.request.GetUserGoogleFitDataRequest;
import com.fitlers.model.request.ShortMessageServiceRequest;
import com.fitlers.model.response.GetUserGoogleFitDataResponse;
import com.fitlers.model.response.ShortMessageServiceResponse;

@Component
public class ServiceProxy {

	public static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

	@Autowired
	private ShortMessageServiceClient shortMessageServiceClient;
	
	@Autowired
	private GoogleFitDataServiceClient googleFitDataServiceClient;

	public boolean sendShortMessage(ShortMessageServiceRequest request) {
		logger.info("ServiceProxy sendShortMessage Calling SMS Gateway");
		ShortMessageServiceResponse response = shortMessageServiceClient.sendShortMessage(
				"GEXFBet4nd3PCAviMYjgqzIV5mrU7ksK2chR0a6WZ1oQTNylLDZVHKNXMO01UsitDzQfg2urYbpmS85E", request);
		logger.info("ServiceProxy sendShortMessage Call to SMS Gateway Completed");
		boolean status = Boolean.parseBoolean(response.getStatus());
		if (!status) {
			logger.error("ServiceProxy sendShortMessage Call to SMS Gateway Failed " + response.getMessage());
		}
		return status;
	}
	
	public GetUserGoogleFitDataResponse getGoogleFitData(GetUserGoogleFitDataRequest request) {
		logger.info("ServiceProxy getGoogleFitData Calling SMS Gateway");
		GetUserGoogleFitDataResponse response = googleFitDataServiceClient.getGoogleFitData(
				"Bearer ya29.a0ARrdaM8MKWb4VwpLdn-z2DFyZ4o48lhHWAPcgCbkc6IUWhNV5I5cA6KY_MZOMz7MTh3ezn9Sm4PgLLiSaNLGyCODAIC1fWK2tksyKBBFwB7615pbt8P7cXphX5Horn8DPK3-RXUPONza5zy54XGynAAy-Qdw", request);
		logger.info("ServiceProxy getGoogleFitData Call to Google API Completed");
		FitError error = response.getError();
		if (error!=null) {
			logger.error("ServiceProxy getGoogleFitData Call to Google API Failed " + response.getError().getMessage());
		}
		return response;
	}
}
