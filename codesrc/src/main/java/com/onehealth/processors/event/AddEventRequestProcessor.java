package com.onehealth.processors.event;

import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.event.AddEventDetailsRequest;

@Component
public class AddEventRequestProcessor extends RequestProcessor<AddEventDetailsRequest, Long> {

	@Override
	public Long doProcessing(AddEventDetailsRequest request) throws Exception {
		
		return null;
	}



}
