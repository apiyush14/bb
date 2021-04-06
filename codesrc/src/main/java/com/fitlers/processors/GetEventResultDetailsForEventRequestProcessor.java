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
import com.fitlers.entities.EventResultDetailsWithUserDetails;
import com.fitlers.model.request.GetEventResultDetailsForEventRequest;
import com.fitlers.model.response.GetEventResultDetailsForEventResponse;
import com.fitlers.repo.EventResultDetailsWithUserDetailsRepo;

@Component
public class GetEventResultDetailsForEventRequestProcessor
		extends RequestProcessor<GetEventResultDetailsForEventRequest, GetEventResultDetailsForEventResponse> {

	@Autowired
	private EventResultDetailsWithUserDetailsRepo eventResultDetailsWithUserDetailsRepository;

	public static final Logger logger = LoggerFactory.getLogger(GetEventResultDetailsForEventRequestProcessor.class);

	@Override
	public GetEventResultDetailsForEventResponse doProcessing(GetEventResultDetailsForEventRequest request)
			throws Exception {
		logger.info("GetEventResultDetailsForEventRequestProcessor doProcessing Started for Event Id "
				+ request.getEventId());
		GetEventResultDetailsForEventResponse response = new GetEventResultDetailsForEventResponse();
		EventResultDetailsWithUserDetails eventResultDetailsWithUserDetailsQueryObj = new EventResultDetailsWithUserDetails();
		eventResultDetailsWithUserDetailsQueryObj.setEventId(Long.parseLong(request.getEventId()));

		Example<EventResultDetailsWithUserDetails> eventResultDetailsWithUserDetailsQueryExample = Example
				.of(eventResultDetailsWithUserDetailsQueryObj);
		List<EventResultDetailsWithUserDetails> eventResultDetailsWithUserDetailsList;

		if (Objects.isNull(request.getPageNumber())) {
			eventResultDetailsWithUserDetailsList = eventResultDetailsWithUserDetailsRepository
					.findAll(eventResultDetailsWithUserDetailsQueryExample, Sort.by("userRank"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("userRank").descending());
			Page<EventResultDetailsWithUserDetails> page = eventResultDetailsWithUserDetailsRepository
					.findAll(eventResultDetailsWithUserDetailsQueryExample, pageRequest);
			eventResultDetailsWithUserDetailsList = page.getContent();
		}

		response.setEventResultDetailsWithUserDetails(eventResultDetailsWithUserDetailsList);
		if (CollectionUtils.isEmpty(eventResultDetailsWithUserDetailsList)
				|| eventResultDetailsWithUserDetailsList.size() < 3) {
			response.setMoreContentAvailable(false);
		} else {
			response.setMoreContentAvailable(true);
		}
		logger.info("GetEventResultDetailsForEventRequestProcessor doProcessing Completed for Event Id "
				+ request.getEventId());
		return response;
	}

}
