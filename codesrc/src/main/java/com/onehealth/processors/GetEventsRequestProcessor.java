package com.onehealth.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.model.response.GetEventDetailsResponse;
import com.onehealth.repo.EventDetailsRepository;

@Component
public class GetEventsRequestProcessor extends RequestProcessor<BaseRequestProcessorInput, GetEventDetailsResponse> {

	@Autowired
	EventDetailsRepository eventDetailsRepository;
	
	@Override
	public GetEventDetailsResponse doProcessing(BaseRequestProcessorInput request) throws Exception {
		GetEventDetailsResponse eventDetailsResponse=new GetEventDetailsResponse();
		List<EventDetails> eventDetailsList=eventDetailsRepository.findAll();
		eventDetailsResponse.setEventDetails(eventDetailsList);
		return eventDetailsResponse;
	}

}
