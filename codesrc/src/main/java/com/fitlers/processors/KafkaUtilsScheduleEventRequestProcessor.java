package com.fitlers.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitlers.core.model.BaseRequestProcessorInput;
import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.schedulers.EventResultScheduler;

@Component
public class KafkaUtilsScheduleEventRequestProcessor extends RequestProcessor<BaseRequestProcessorInput, Boolean> {

	@Autowired(required = false)
	private EventResultScheduler eventResultScheduler;

	@Override
	public Boolean doProcessing(BaseRequestProcessorInput request) throws Exception {
		eventResultScheduler.scheduledTask();
		return true;
	}

}
