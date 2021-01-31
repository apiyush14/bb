package com.onehealth.processors;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.onehealth.core.exceptions.NoEventDetailsFoundException;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventResultDetails;
import com.onehealth.model.request.GetEventResultDetailsRequest;
import com.onehealth.model.response.GetEventResultDetailsResponse;
import com.onehealth.repo.EventResultDetailsRepository;

@Component
public class GetEventResultDetailsRequestProcessor
		extends RequestProcessor<GetEventResultDetailsRequest, GetEventResultDetailsResponse> {

	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepo;

	private static final Logger LOG = Logger.getLogger(GetEventResultDetailsRequestProcessor.class);

	@Override
	public GetEventResultDetailsResponse doProcessing(GetEventResultDetailsRequest request) {
		LOG.debug("Getting event result details.");
		GetEventResultDetailsResponse response = new GetEventResultDetailsResponse();
		EventResultDetails eventResultDetailsQueryObj = new EventResultDetails();
		eventResultDetailsQueryObj.setUserId(request.getUserId());
		Example<EventResultDetails> eventResultDetailsQueryExample = Example.of(eventResultDetailsQueryObj);
		List<EventResultDetails> eventResultDetailsList = eventResultDetailsRepo
				.findAll(eventResultDetailsQueryExample);
		
		if(!eventResultDetailsList.isEmpty()) {
		LOG.debug("Event result details retrieved for "+eventResultDetailsList.size()+" event(s)");
		response.setEventResultDetails(eventResultDetailsList);
		}
		else {
		LOG.error("No valid event details found.");	
		throw new NoEventDetailsFoundException();
		}
		return response;
	}

}
