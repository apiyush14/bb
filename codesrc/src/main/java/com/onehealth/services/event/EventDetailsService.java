package com.onehealth.services.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onehealth.core.service.BaseService;
import com.onehealth.model.event.AddEventDetailsRequest;
import com.onehealth.processors.event.AddEventRequestProcessor;
import com.onehealth.processors.event.UploadFileRequestProcessor;

@RestController
public class EventDetailsService extends BaseService {

	@Autowired
    AddEventRequestProcessor addEventRequestProcessor;
	
	@Autowired
	UploadFileRequestProcessor uploadFileRequestProcessor;

	@PutMapping(path = "event-details/addEvent", produces = "application/json")
	public ResponseEntity<Object> addEvent(@RequestBody AddEventDetailsRequest addEventDetailsRequest) {
		addEventRequestProcessor.setRequest(addEventDetailsRequest);
		return execute(addEventRequestProcessor);
	}
	
	@PostMapping(path = "event-details/uploadEventDisplayPic",consumes="multipart/form-data", produces = "application/json")
	public ResponseEntity<Object> uploadEventDisplayPic(@RequestParam("file") MultipartFile file) {
		uploadFileRequestProcessor.setRequest(file);
		return execute(uploadFileRequestProcessor);
	}

}
