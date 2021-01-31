package com.onehealth.processors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.core.exceptions.NoRunSummryFoundException;
import com.onehealth.core.exceptions.NoUserFoundException;
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

	private static final Logger LOG = Logger.getLogger(GetRunSummaryForUserRequestProcessor.class);

	@Override
	public boolean isRequestValid(BaseRequestProcessorInput request) {
		if (StringUtils.isEmpty(request.getUserId())) {
			LOG.error("Invalid user id found, unable to process request");
			throw new NoUserFoundException();
		}
		LOG.debug("Valid get run summary details request, processing..");
		return super.isRequestValid(request);
	}

	@Override
	public GetRunSummaryForUserResponse doProcessing(BaseRequestProcessorInput request) {
		GetRunSummaryForUserResponse response = new GetRunSummaryForUserResponse();
		RunSummary runSummaryOptional= runSummaryRepository.findById(request.getUserId()).orElseThrow(NoRunSummryFoundException::new);
		LOG.debug("Retrieved saved run summary details for user : "+runSummaryOptional.getUserDetails().getUserFirstName()+" "
				+runSummaryOptional.getUserDetails().getUserLastName());
		response.setRunSummary(runSummaryOptional);
		return response;
	}

}
