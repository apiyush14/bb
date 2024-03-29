package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitlers.core.service.BaseService;
import com.fitlers.model.request.AddEventDetailsRequest;
import com.fitlers.model.request.GetDisplayImageForEventRequest;
import com.fitlers.model.request.GetEventsRequest;
import com.fitlers.model.request.UploadEventImageRequest;
import com.fitlers.processors.AddEventRequestProcessor;
import com.fitlers.processors.GetDisplayImageForEventRequestProcessor;
import com.fitlers.processors.GetEventsRequestProcessor;
import com.fitlers.processors.UploadEventImageRequestProcessor;

@RestController
public class EventDetailsService extends BaseService {

	@Autowired
	AddEventRequestProcessor addEventRequestProcessor;

	@Autowired
	UploadEventImageRequestProcessor uploadEventImageRequestProcessor;

	@Autowired
	GetDisplayImageForEventRequestProcessor getDisplayImageForEventRequestProcessor;

	@Autowired
	GetEventsRequestProcessor getEventsRequestProcessor;

	@PutMapping(path = "event-details/addEvent", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
	public ResponseEntity<Object> addEvent(@RequestBody AddEventDetailsRequest addEventDetailsRequest) {
		addEventRequestProcessor.setRequest(addEventDetailsRequest);
		return execute(addEventRequestProcessor);
	}

	@PostMapping(path = "event-details/uploadEventDisplayImage/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadEventDisplayImage(@PathVariable Long eventId,
			@RequestParam(required = true, name = "imageType") String imageType,
			@RequestParam("file") MultipartFile file) {
		UploadEventImageRequest uploadEventImageRequest = new UploadEventImageRequest();
		uploadEventImageRequest.setEventId(eventId);
		uploadEventImageRequest.setImageType(imageType);
		uploadEventImageRequest.setFile(file);
		uploadEventImageRequestProcessor.setRequest(uploadEventImageRequest);
		return execute(uploadEventImageRequestProcessor);
	}

	@GetMapping(path = "event-details/getDisplayImage/{eventId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Object> getDisplayImageForEvent(@PathVariable Long eventId,
			@RequestParam(required = true, name = "imageType") String imageType) {
		GetDisplayImageForEventRequest request = new GetDisplayImageForEventRequest();
		request.setEventId(eventId);
		request.setImageType(imageType);
		getDisplayImageForEventRequestProcessor.setRequest(request);
		return execute(getDisplayImageForEventRequestProcessor);
	}

	@GetMapping(path = "event-details/getEvents/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEvents(@PathVariable String userId,
			@RequestParam(required = false, name = "page") String pageNumber) {
		GetEventsRequest request = new GetEventsRequest();
		request.setPageNumber(pageNumber);
		request.setUserId(userId);
		getEventsRequestProcessor.setRequest(request);
		return execute(getEventsRequestProcessor);
	}
}
