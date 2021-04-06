package com.fitlers.processors;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.EventResultDetails;
import com.fitlers.model.request.GetEventResultDetailsRequest;
import com.fitlers.model.response.GetEventResultDetailsResponse;
import com.fitlers.repo.EventResultDetailsRepository;

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
		List<EventResultDetails> eventResultDetailsList;
		if (Objects.isNull(request.getPageNumber())) {
			eventResultDetailsList = eventResultDetailsRepo.findAll(eventResultDetailsQueryExample,
					Sort.by("createdDate"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("createdDate").descending());
			Page<EventResultDetails> page = eventResultDetailsRepo.findAll(eventResultDetailsQueryExample, pageRequest);
			eventResultDetailsList = page.getContent();
		}
		response.setEventResultDetails(eventResultDetailsList);
		if (CollectionUtils.isEmpty(eventResultDetailsList) || eventResultDetailsList.size() < 3) {
			response.setMoreContentAvailable(false);
		} else {
			response.setMoreContentAvailable(true);
		}
		logger.info("GetEventResultDetailsRequestProcessor doProcessing Completed for User Id " + request.getUserId());
		return response;
	}

}