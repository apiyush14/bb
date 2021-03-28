package com.onehealth.processors;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.core.model.BaseRequestProcessorInput;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.RunSummary;
import com.onehealth.model.response.GetRunSummaryForUserResponse;
import com.onehealth.repo.RunSummaryRepository;

@Component
public class GetRunSummaryForUserRequestProcessor
		extends RequestProcessor<BaseRequestProcessorInput, GetRunSummaryForUserResponse> {

	@Autowired
	RunSummaryRepository runSummaryRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(GetRunSummaryForUserRequestProcessor.class);

	@Override
	public boolean isRequestValid(BaseRequestProcessorInput request) throws Exception {
		if (StringUtils.isEmpty(request.getUserId())) {
			throw new Exception("User Id is not populated");
		}
		return super.isRequestValid(request);
	}

	@Override
	public GetRunSummaryForUserResponse doProcessing(BaseRequestProcessorInput request) throws Exception {
		logger.info("GetRunSummaryForUserRequestProcessor doProcessing Started for User Id " + request.getUserId());
		GetRunSummaryForUserResponse response = new GetRunSummaryForUserResponse();
		Optional<RunSummary> runSummaryOptional= runSummaryRepository.findById(request.getUserId());
		if(runSummaryOptional.isPresent()) {
		  response.setRunSummary(runSummaryRepository.findById(request.getUserId()).get());
		}
		logger.info("GetRunSummaryForUserRequestProcessor doProcessing Started for User Id " + request.getUserId());
		return response;
	}

}
