package com.onehealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.exceptions.NoDataFoundException;
import com.onehealth.core.service.BaseService;
import com.onehealth.model.request.GetEventResultDetailsRequest;
import com.onehealth.model.response.GetEventResultDetailsResponse;
import com.onehealth.processors.GetEventResultDetailsRequestProcessor;
import static com.onehealth.services.ServiceConsts.*;

@RestController
public class EventResultDetailsService extends BaseService {

	@Autowired
	private GetEventResultDetailsRequestProcessor getEventResultDetailsRequestProcessor;

	@GetMapping(path = EVENT_RESULTS+"/{userId}", produces = "application/json")
	public ResponseEntity<Object> getEventResultDetailsForUser(@PathVariable String userId) {
		GetEventResultDetailsRequest request = new GetEventResultDetailsRequest();
		request.setUserId(userId);
		getEventResultDetailsRequestProcessor.setRequest(request);
		return execute(getEventResultDetailsRequestProcessor);
	}

	@GetMapping(path = EVENT_RESULTS+"/{eventId}")
	public ResponseEntity<Object> getEventResultDetailsPageable(
			@PathVariable(required=true) Long eventId,
			@RequestParam(required=true) Integer pageNumber,
			@RequestParam(required=true) Integer pageSize
			) throws NoDataFoundException {
		
		GetEventResultDetailsRequest request = new GetEventResultDetailsRequest();
		request.setEventId(eventId);
		request.setPageNumber(pageNumber);
		request.setPageSize(pageSize);
		
		GetEventResultDetailsResponse eventResultDetailsResponse = getEventResultDetailsRequestProcessor.getEventResultDetailsPageable(request);
		return ResponseEntity.ok(eventResultDetailsResponse);
	}
}
