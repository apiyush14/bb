package com.fitlers.processors;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.entities.EventDetails;
import com.fitlers.model.request.GetEventsRequest;
import com.fitlers.model.response.GetEventDetailsResponse;
import com.fitlers.repo.EventDetailsRepository;

@Component
public class GetEventsRequestProcessor extends RequestProcessor<GetEventsRequest, GetEventDetailsResponse> {

	@Autowired
	EventDetailsRepository eventDetailsRepository;

	public static final Logger logger = LoggerFactory.getLogger(GetEventsRequestProcessor.class);

	@Override
	public GetEventDetailsResponse doProcessing(GetEventsRequest request) throws Exception {
		logger.info("GetEventsRequestProcessor doProcessing Started for User Id " + request.getUserId());
		GetEventDetailsResponse eventDetailsResponse = new GetEventDetailsResponse();

		List<EventDetails> eventDetailsList;
		if (Objects.isNull(request.getPageNumber())) {
			eventDetailsList = eventDetailsRepository.findAllEligibleEvents(Sort.by("eventStartDate"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("eventStartDate"));
			Page<EventDetails> page = eventDetailsRepository.findAllEligibleEvents(pageRequest);
			eventDetailsList = page.getContent();
		}
		eventDetailsResponse.setEventDetails(eventDetailsList);
		if (CollectionUtils.isEmpty(eventDetailsList) || eventDetailsList.size() < 3) {
			eventDetailsResponse.setMoreContentAvailable(false);
		} else {
			eventDetailsResponse.setMoreContentAvailable(true);
		}
		logger.info("GetEventsRequestProcessor doProcessing Completed for User Id " + request.getUserId());
		return eventDetailsResponse;
	}

}
