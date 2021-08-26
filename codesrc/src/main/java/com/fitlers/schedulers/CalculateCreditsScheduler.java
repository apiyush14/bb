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
import org.springframework.transaction.annotation.Transactional;

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
                
				//TODO Rather than using forEach, we should get all RunDetails and Run Summaries at once using findAll
				eventResultDetailsList.forEach(eventResultDetails -> populateRunDetailsList(eventResultDetails));
				//runDetailsRepo.findAllById(null)
				UpdateCreditsFork updateCreditsForkObj = new UpdateCreditsFork(null, null, null, eventResultDetailsList,
						runSummaryList, runDetailsList,eventResultDetailsRepository, runDetailsRepo, runSummaryRepository);

				ForkJoinPool pool = new ForkJoinPool(100);
				logger.info("ProcessStarted at :" + new Date());

				pool.invoke(updateCreditsForkObj);
				while (!pool.isQuiescent());

				logger.info("ProcessCompleted at :" + new Date());
				completedEvent.setIsCreditCalculated("Y");

			}

			if (completedEvent != null)
				eventDetailsRepository.save(completedEvent);
		
		}

	}

	private void populateRunDetailsList(EventResultDetails eventResultDetails) {
		RunDetailsId runDetailsId = new RunDetailsId(eventResultDetails.getRunId(), eventResultDetails.getUserId());
		
		Optional<RunDetails> runDetails = runDetailsRepo.findById(runDetailsId);
		if (runDetails.isPresent()) {
			runDetailsList.add(runDetails.get());
		}

		Optional<RunSummary> runSummary = runSummaryRepository.findById(eventResultDetails.getUserId());
		if (runSummary.isPresent()) {
			runSummaryList.add(runSummary.get());
		}
	}

	


	@Scheduled(cron = "0/30 * * * * ?")
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

