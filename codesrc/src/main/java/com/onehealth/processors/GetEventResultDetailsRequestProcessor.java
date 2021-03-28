package com.onehealth.processors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import com.onehealth.core.exceptions.NoDataFoundException;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.EventResultDetails;
import com.onehealth.model.request.GetEventResultDetailsRequest;
import com.onehealth.model.response.GetEventResultDetailsResponse;
import com.onehealth.repo.EventResultDetailsRepository;
import static com.onehealth.processors.ProcessorUtils.getDefaultPageNumber;
import static com.onehealth.processors.ProcessorUtils.getDefaultPageSize;

@Component
public class GetEventResultDetailsRequestProcessor
		extends RequestProcessor<GetEventResultDetailsRequest, GetEventResultDetailsResponse> {

	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepo;
	
	private static final Logger LOG = Logger.getLogger(GetEventResultDetailsRequestProcessor.class);

	@Override
	public GetEventResultDetailsResponse doProcessing(GetEventResultDetailsRequest request) throws Exception {
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
			throw new NoDataFoundException();
			}
			return response;
	}

	public GetEventResultDetailsResponse getEventResultDetailsPageable(
			GetEventResultDetailsRequest eventResultDetailsreq) throws NoDataFoundException {

		LOG.debug("Getting event result details for event :"+eventResultDetailsreq.getEventId()+" with page arguments"+ eventResultDetailsreq.getPageNumber()+","+
				eventResultDetailsreq.getPageSize());
		GetEventResultDetailsResponse pageableEventResultDetails = new GetEventResultDetailsResponse();

		Pageable pageable = PageRequest.of(getDefaultPageNumber(eventResultDetailsreq.getPageNumber()),
				getDefaultPageSize(eventResultDetailsreq.getPageSize()), Sort.by("userRank"));
		Page<EventResultDetails> page = eventResultDetailsRepo
				.findByEventId(eventResultDetailsreq.getEventId(), pageable);

		if (!page.isEmpty())
			pageableEventResultDetails.setEventResultDetails(page.getContent());
		else {
			LOG.error("No results were found for event !");	
			throw new NoDataFoundException();
		}
		LOG.debug("Response contain :"+pageableEventResultDetails.getEventResultDetails().size()+" records");
		return pageableEventResultDetails;
	}

}
