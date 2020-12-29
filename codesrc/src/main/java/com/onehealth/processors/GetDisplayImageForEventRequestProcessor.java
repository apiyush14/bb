package com.onehealth.processors;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.model.request.GetDisplayImageForEventRequest;
import com.onehealth.repo.EventDetailsRepository;

@Component
public class GetDisplayImageForEventRequestProcessor extends RequestProcessor<GetDisplayImageForEventRequest, byte[]>{

	@Autowired
	EventDetailsRepository eventDetailsRepository;
	
	@Override
	public byte[] doProcessing(GetDisplayImageForEventRequest request) throws Exception {
		EventDetails eventDetails=eventDetailsRepository.getOne(request.getEventId());
		InputStream in = new FileInputStream(eventDetails.getEventDisplayPic());
		return StreamUtils.copyToByteArray(in);
	}

}
