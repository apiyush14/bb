package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
			if (!CollectionUtils.isEmpty(eventResultDetailsList)) {
				List<RunDetailsId> runDetailsIdList = new ArrayList<RunDetailsId>();
				List<String> userIdList = new ArrayList<String>();
				eventResultDetailsList.forEach(
						eventResultDetails -> populateRunDetailsList(eventResultDetails, runDetailsIdList, userIdList));
				runDetailsList = runDetailsRepo.findAllById(runDetailsIdList);
				runSummaryList = runSummaryRepository.findAllById(userIdList);

				Map<String, RunDetails> runDetailsMap = runDetailsList.stream()
						.collect(Collectors.toMap(runDet -> runDet.getUserId(), runDet -> runDet));
				Map<String, RunSummary> runSummaryMap = runSummaryList.stream()
						.collect(Collectors.toMap(runSum -> runSum.getUserId(), runSum -> runSum));

				List<UpdateUserCreditsFork> updateUserCreditsTasksList = new ArrayList<UpdateUserCreditsFork>();

				for (EventResultDetails erd : this.eventResultDetailsList) {

					RunDetails runDetails = runDetailsMap.get(erd.getUserId());
					RunSummary runSummary = runSummaryMap.get(erd.getUserId());

					if (!Objects.isNull(runDetails) && (!Objects.isNull(runSummary))) {
						UpdateUserCreditsFork subtask = new UpdateUserCreditsFork(erd, runDetails, runSummary,
								eventResultDetailsList.size(), eventResultDetailsRepository, runDetailsRepo,
								runSummaryRepository);
						updateUserCreditsTasksList.add(subtask);
					}
				}

				ForkJoinPool pool = new ForkJoinPool(20);
				logger.info("ProcessStarted at :" + new Date());

				pool.invokeAll(updateUserCreditsTasksList);

				while (!pool.isQuiescent())
					;

				logger.info("ProcessCompleted at :" + new Date());
				completedEvent.setIsCreditCalculated("Y");

			}

			if (completedEvent != null)
				eventDetailsRepository.save(completedEvent);

		}

	}

	private void populateRunDetailsList(EventResultDetails eventResultDetails, List<RunDetailsId> runDetailsIdList,
			List<String> userIdList) {
		RunDetailsId runDetailsId = new RunDetailsId(eventResultDetails.getRunId(), eventResultDetails.getUserId());
		runDetailsIdList.add(runDetailsId);
		userIdList.add(eventResultDetails.getUserId());
	}

	@Scheduled(cron = "* 0/30 * * * ?")
	public void scheduledTaskForClass() {
		eventResultDetailsList = new ArrayList<EventResultDetails>();
		completedEventsList = eventDetailsRepository.findAllCompletedEvents();
		if (!completedEventsList.isEmpty()) {
			calculateCreditsForUserRun();
			logger.info("Calculated Event Credits at " + new Date());
		}
	}

}
