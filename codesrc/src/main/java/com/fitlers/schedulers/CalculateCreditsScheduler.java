package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fitlers.entities.EventDetails;
import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunDetailsId;
import com.fitlers.entities.RunSummary;
import com.fitlers.repo.EventDetailsRepository;
import com.fitlers.repo.EventResultDetailsRepository;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;


@Component
public class CalculateCreditsScheduler {

	@Autowired
	private EventDetailsRepository eventDetailsRepository;

	@Autowired
	private RunDetailsRepository runDetailsRepo;

	@Autowired
	private RunSummaryRepository runSummaryRepository;

	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(CalculateCreditsScheduler.class);

	TaskScheduler taskScheduler;
	private List<RunSummary> runSummaryList;
	private List<RunDetails> runDetailsList;
	private List<EventResultDetails> eventResultDetailsList;;
	private List<EventDetails> completedEventsList;

	private void calculateCreditsForUserRun() {
		for (EventDetails completedEvent : completedEventsList) {
			EventResultDetails eventResultDetailsQueryObj = new EventResultDetails();
			eventResultDetailsQueryObj.setEventId(completedEvent.getEventId());
			Example<EventResultDetails> eventResultDetailsQueryExample = Example.of(eventResultDetailsQueryObj);

			eventResultDetailsList = eventResultDetailsRepository.findAll(eventResultDetailsQueryExample);
			if (eventResultDetailsList != null && !eventResultDetailsList.isEmpty()) {
				runSummaryList = new ArrayList<RunSummary>();
				runDetailsList = new ArrayList<RunDetails>();
                
				
				List<RunDetailsId> runDetailsIdList = new ArrayList();
				List<String> userIdList = new ArrayList();
				eventResultDetailsList.forEach(eventResultDetails -> populateRunDetailsList(eventResultDetails,runDetailsIdList,userIdList));
				runDetailsList = runDetailsRepo.findAllById(runDetailsIdList);
				runSummaryList = runSummaryRepository.findAllById(userIdList);

			
			List<UpdateUserCreditsFork> listTestFork = new ArrayList();
			
			
			for (EventResultDetails erd : this.eventResultDetailsList) {

				RunDetails runDetailsObj = null ;
				RunSummary existingRunSummary = null;
				Optional<RunDetails> runDetails = runDetailsList.stream()
						.filter(runDet -> (runDet.getUserId().equals(erd.getUserId())
								&& runDet.getRunId().equals(erd.getRunId())))
						.findFirst();
				if (runDetails.isPresent()) {
					 runDetailsObj =runDetails.get();
				}
				
				Optional<RunSummary> existingRunSummaryOptional = runSummaryList.stream()
						.filter(runSum -> (runSum.getUserId().equals(erd.getUserId()))).findFirst();
				if (existingRunSummaryOptional.isPresent()) {
					 existingRunSummary = existingRunSummaryOptional.get();
					
				}
				UpdateUserCreditsFork subtask = new UpdateUserCreditsFork(erd, runDetailsObj, existingRunSummary, eventResultDetailsList.size(),
						eventResultDetailsRepository, runDetailsRepo, runSummaryRepository);
				listTestFork.add(subtask);
			}
			
			
				ForkJoinPool pool = new ForkJoinPool(100);
				logger.info("ProcessStarted at :" + new Date());

				pool.invokeAll(listTestFork);
				
				while (!pool.isQuiescent());

				logger.info("ProcessCompleted at :" + new Date());
				completedEvent.setIsCreditCalculated("Y");

			}

			if (completedEvent != null)
				eventDetailsRepository.save(completedEvent);
		
		}

	}

	private void populateRunDetailsList(EventResultDetails eventResultDetails, List<RunDetailsId> runDetailsIdList, List<String> userIdList) {
		RunDetailsId runDetailsId = new RunDetailsId(eventResultDetails.getRunId(), eventResultDetails.getUserId());
		
		runDetailsIdList.add(runDetailsId);

		userIdList.add(eventResultDetails.getUserId());
		
	}

	


	@Scheduled(cron = "* 0/30 * * * ?")
	public void scheduledTaskForClass() {
		eventResultDetailsList = new ArrayList<EventResultDetails>();
		completedEventsList = new ArrayList<EventDetails>();
		completedEventsList = eventDetailsRepository.findAllCompletedEvents();
		if (!completedEventsList.isEmpty()) {
			calculateCreditsForUserRun();
			logger.info("Calculated Event Credits at " + new Date());
		}
	}

}

