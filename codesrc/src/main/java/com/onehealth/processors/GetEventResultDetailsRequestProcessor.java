package com.onehealth.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

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
	
	public static final Logger logger = LoggerFactory.getLogger(GetEventResultDetailsRequestProcessor.class);

	@Override
	public GetEventResultDetailsResponse doProcessing(GetEventResultDetailsRequest request) throws Exception {
		logger.info("GetEventResultDetailsRequestProcessor doProcessing Started for User Id " + request.getUserId());
		GetEventResultDetailsResponse response = new GetEventResultDetailsResponse();
		EventResultDetails eventResultDetailsQueryObj = new EventResultDetails();
		eventResultDetailsQueryObj.setUserId(request.getUserId());
		Example<EventResultDetails> eventResultDetailsQueryExample = Example.of(eventResultDetailsQueryObj);
		List<EventResultDetails> eventResultDetailsList = eventResultDetailsRepo
				.findAll(eventResultDetailsQueryExample);
		response.setEventResultDetails(eventResultDetailsList);
		logger.info("GetEventResultDetailsRequestProcessor doProcessing Completed for User Id " + request.getUserId());
		return response;
	}

}
