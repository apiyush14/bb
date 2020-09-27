package com.onehealth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.entities.RunDetails;
import com.onehealth.model.AddRunDetailsRequest;
import com.onehealth.processors.AddRunRequestProcessor;
import com.onehealth.repo.RunDetailsRepository;

@RestController
public class RunDetailsService {

@Autowired
AddRunRequestProcessor addRunRequestProcessor;

@Autowired
RunDetailsRepository runDetailsRepository;
	
@PostMapping("run-details/addRuns")
public ResponseEntity<Object> addRun(@RequestBody AddRunDetailsRequest runDetails) {
 runDetailsRepository.saveAll(runDetails.getRunDetailsList());
 return null;
}

@GetMapping("run-details/user/{userId}/getAllRuns")
public List<RunDetails> getAllRunsForUser(@PathVariable String userId, @RequestParam(required=false,name="page") String pageNumber) {
 RunDetails runDetails=new RunDetails();
 runDetails.setUserId(userId);
 ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("runId");
 Example<RunDetails> example= Example.of(runDetails, matcher);
 if(null==pageNumber) {
	 return runDetailsRepository.findAll(example,Sort.by("runId"));
 }
 PageRequest pageRequest=PageRequest.of(Integer.parseInt(pageNumber), 10,Sort.by("runId"));
 Page<RunDetails> page=runDetailsRepository.findAll(example,pageRequest);
 return page.getContent();
 //return runDetailsRepository.findAll();
}

@GetMapping("run-details")
public String testMethod() {
 return "Hello Test";
}
	
}
