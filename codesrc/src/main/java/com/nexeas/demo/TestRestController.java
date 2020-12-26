package com.nexeas.demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onehealth.core.kafka.KafkaUtils;
import com.onehealth.entities.RunDetails;
import com.onehealth.entities.event.EventResultDetails;

@RestController
public class TestRestController {
	
	@Autowired
	AdminClient adminClient;
	
	@Autowired
	KafkaUtils kafkaUtils;

	@GetMapping(path="/testMethod")
	public String testMethod()
	{
		kafkaUtils.createNewKafkaTopic("EVENT_RUN_SUBMISSION61");
		//kafkaUtils.createNewKafkaTopic("testTopic");
		kafkaUtils.createAndStartNewStream("EVENT_RUN_SUBMISSION61",61);
		return "Hello World From One Health!!!";
	}
	
	@GetMapping(path="/testMethod1")
	public String testMethod1()
	{
		RunDetails rundetails= new RunDetails();
		rundetails.setUserId("48ffc00a-5047-4ad5-bc05-22714ac3d579");
		rundetails.setRunTotalTime("233324");
		rundetails.setEventId((long) 61);
		//kafkaUtils.sendMessage("testTopic", "testMessage");
		kafkaUtils.sendMessage("EVENT_RUN_SUBMISSION61", rundetails);
		return "Hello World From One Health!!!";
	}
	
	@GetMapping(path="/testMethod2")
	public String testMethod2()
	{
		RunDetails rundetails= new RunDetails();
		rundetails.setUserId("5bbce9c1-fcd5-4ca3-b2ba-f9db4f93668e");
		rundetails.setRunTotalTime("345353");
		rundetails.setEventId((long) 61);
		//kafkaUtils.sendMessage("testTopic", "testMessage");
		kafkaUtils.sendMessage("EVENT_RUN_SUBMISSION61", rundetails);
		return "Hello World From One Health!!!";
	}
	
	@GetMapping(path="/testMethod3")
	public String testMethod3() throws Exception
	{
		EventResultDetails eventResultDetails=new EventResultDetails();
		eventResultDetails.setUserId("48ffc00a-5047-4ad5-bc05-22714ac3d579");
		eventResultDetails.setEventId((long) 61);
		eventResultDetails.setRunId((long) 23423);
		eventResultDetails.setUserRank((long)11);
		
		kafkaUtils.sendMessage("EVENT_RESULTS", eventResultDetails);
		return "Hello World From One Health!!!";
	}
}
