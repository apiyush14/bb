package com.onehealth.services.event;

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

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.service.BaseService;
import com.onehealth.model.event.AddEventDetailsRequest;
import com.onehealth.model.event.GetDisplayImageForEventRequest;
import com.onehealth.model.event.UploadEventImageRequest;
import com.onehealth.processors.event.AddEventRequestProcessor;
import com.onehealth.processors.event.GetDisplayImageForEventRequestProcessor;
import com.onehealth.processors.event.GetEventsRequestProcessor;
import com.onehealth.processors.event.UploadEventImageRequestProcessor;

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

	@PutMapping(path = "event-details/addEvent", produces = "application/json")
	@CrossOrigin(origins="*")
	public ResponseEntity<Object> addEvent(@RequestBody AddEventDetailsRequest addEventDetailsRequest) {
		addEventRequestProcessor.setRequest(addEventDetailsRequest);
		return execute(addEventRequestProcessor);
	}
	
	@PostMapping(path = "event-details/uploadEventDisplayImage/{eventId}",consumes="multipart/form-data", produces = "application/json")
	public ResponseEntity<Object> uploadEventDisplayImage(@PathVariable Long eventId,@RequestParam("file") MultipartFile file) {
		UploadEventImageRequest uploadEventImageRequest=new UploadEventImageRequest();
		uploadEventImageRequest.setEventId(eventId);
		uploadEventImageRequest.setFile(file);
		uploadEventImageRequestProcessor.setRequest(uploadEventImageRequest);
		return execute(uploadEventImageRequestProcessor);
	}
	
	@GetMapping(path="event-details/getDisplayImage/{eventId}",produces=MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Object> getDisplayImageForEvent(@PathVariable Long eventId){
		GetDisplayImageForEventRequest request=new GetDisplayImageForEventRequest();
		request.setEventId(eventId);
		getDisplayImageForEventRequestProcessor.setRequest(request);
		return execute(getDisplayImageForEventRequestProcessor);
	}

	@GetMapping(path="event-details/getEvents",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEvents(){
		getEventsRequestProcessor.setRequest(new BaseRequestProcessorInput());
		return execute(getEventsRequestProcessor);
	}
}
