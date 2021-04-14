package com.fitlers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitlers.core.model.BaseRequestProcessorInput;
import com.fitlers.core.service.BaseService;
import com.fitlers.processors.KafkaUtilsScheduleEventRequestProcessor;

@RestController
public class KafkaUtilsService extends BaseService {

	@Autowired
	private KafkaUtilsScheduleEventRequestProcessor kafkaUtilsScheduleEventRequestProcessor;

	@GetMapping(path = "event/scheduleEvent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> scheduleEvent() {
		kafkaUtilsScheduleEventRequestProcessor.setRequest(new BaseRequestProcessorInput());
		return execute(kafkaUtilsScheduleEventRequestProcessor);
	}
}
