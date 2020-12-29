package com.onehealth.processors;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.entities.RunDetails;
import com.onehealth.model.request.GetRunsForUserRequest;
import com.onehealth.model.response.GetRunsForUserResponse;
import com.onehealth.repo.RunDetailsRepository;

@Component
public class GetRunsForUserRequestProcessor extends RequestProcessor<GetRunsForUserRequest, GetRunsForUserResponse> {

	@Autowired
	RunDetailsRepository runDetailsRepository;
	
	@Override
	public boolean isRequestValid(GetRunsForUserRequest request) throws Exception {
		if (StringUtils.isEmpty(request.getUserId())) {
			throw new Exception("User Id is not populated");
		}
		return super.isRequestValid(request);
	}

	@Override
	public GetRunsForUserResponse doProcessing(GetRunsForUserRequest request) throws Exception {
		GetRunsForUserResponse response = new GetRunsForUserResponse();
		RunDetails runDetailsQueryObj = new RunDetails();
		runDetailsQueryObj.setUserId(request.getUserId());
		Example<RunDetails> runDetailsQueryExample = Example.of(runDetailsQueryObj);
		List<RunDetails> runDetailsList;

		if (Objects.isNull(request.getPageNumber())) {
			runDetailsList = runDetailsRepository.findAll(runDetailsQueryExample, Sort.by("runId"));
		} else {
			// TODO Configure correct page size
			PageRequest pageRequest = PageRequest.of(Integer.parseInt(request.getPageNumber()), 3,
					Sort.by("runId").descending());
			Page<RunDetails> page = runDetailsRepository.findAll(runDetailsQueryExample, pageRequest);
			runDetailsList = page.getContent();
		}
		response.setRunDetailsList(runDetailsList);
		return response;
	}

}
