package com.fitlers.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.model.request.ShortMessageServiceRequest;
import com.fitlers.model.response.ShortMessageServiceResponse;

@Component
public class ServiceProxy {

	public static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

	@Autowired
	private ShortMessageServiceClient shortMessageServiceClient;

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
}
