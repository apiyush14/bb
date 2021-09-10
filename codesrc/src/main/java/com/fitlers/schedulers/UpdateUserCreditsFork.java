package com.fitlers.schedulers;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunSummary;
import com.fitlers.repo.EventResultDetailsRepository;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;

public class UpdateUserCreditsFork implements Callable<Boolean> {

	private RunDetailsRepository runDetailsRepo;
	private RunSummaryRepository runSummaryRepository;
	private EventResultDetailsRepository eventResultDetailsRepository;
	private static final int totalCreditsForEvent = 5000;
	private RunSummary runSummary;
	private RunDetails runDetailsObj;
	private int size;
	private EventResultDetails eventResultsDetails;
	public static final Logger logger = LoggerFactory.getLogger(UpdateUserCreditsFork.class);

	public UpdateUserCreditsFork(EventResultDetails eventResultsDetails, RunDetails runDetailsObj,
			RunSummary existingRunSummary, int size, EventResultDetailsRepository eventResultDetailsRepository,
			RunDetailsRepository runDetailsRepo, RunSummaryRepository runSummaryRepository) {

		this.runSummary = existingRunSummary;
		this.runDetailsObj = runDetailsObj;
		this.eventResultDetailsRepository = eventResultDetailsRepository;
		this.runDetailsRepo = runDetailsRepo;
		this.runSummaryRepository = runSummaryRepository;
		this.size = size;
		this.eventResultsDetails = eventResultsDetails;
	}

	@Override
	public Boolean call() throws Exception {
		updateCredits(runDetailsObj, runSummary, size, eventResultsDetails);
		return true;
	}

	private void updateCredits(RunDetails runDetailsObj, RunSummary runSummary, int size,
			EventResultDetails eventResultDetailsObj) {
		logger.info(Thread.currentThread().getName() + "START - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId());
		/* Starting run details and run summary credit calculation */
		Long userRank = eventResultDetailsObj.getUserRank();
		double multiplier = 0.6;
		Integer runCreditsForUser = (int)Math.round(((1 - multiplier) / (1 - Math.pow(multiplier, size))
				* Math.pow(multiplier, (userRank - 1))) * totalCreditsForEvent);

		eventResultDetailsObj.setRunCredits(runCreditsForUser);
		runDetailsObj.setRunCredits(runCreditsForUser);
		runSummary.setTotalCredits(runSummary.getTotalCredits() + runCreditsForUser);

		saveCredits();

		/* Ending run details and run summary credit calculation */
		logger.info(Thread.currentThread().getName() + "END - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId());
	}

	@Transactional
	private void saveCredits() {
		eventResultDetailsRepository.save(this.eventResultsDetails);
		runSummaryRepository.save(this.runSummary);
		runDetailsRepo.save(this.runDetailsObj);
	}

}
