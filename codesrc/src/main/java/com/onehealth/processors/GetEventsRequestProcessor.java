package com.onehealth.processors;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventDetails;
import com.onehealth.model.request.GetEventsRequest;
import com.onehealth.model.response.GetEventDetailsResponse;
import com.onehealth.repo.EventDetailsRepository;

@Component
public class GetEventsRequestProcessor extends RequestProcessor<GetEventsRequest, GetEventDetailsResponse> {

	@Autowired
	EventDetailsRepository eventDetailsRepository;
	
	@Override
	public GetEventDetailsResponse doProcessing(GetEventsRequest request) throws Exception {
		GetEventDetailsResponse eventDetailsResponse=new GetEventDetailsResponse();
		
		List<EventDetails> eventDetailsList;
		if (Objects.isNull(request.getPageNumber())) {
			  eventDetailsList=eventDetailsRepository.findAllEligibleEvents(Sort.by("eventStartDate"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("eventStartDate"));
			Page<EventDetails> page=eventDetailsRepository.findAllEligibleEvents(pageRequest);
			eventDetailsList = page.getContent();
		}
		
		eventDetailsResponse.setEventDetails(eventDetailsList);
		return eventDetailsResponse;
	}

}
