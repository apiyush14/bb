package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunSummary;
import com.fitlers.repo.EventResultDetailsRepository;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;

public class UpdateCreditsFork extends RecursiveAction {

	
	private RunDetailsRepository runDetailsRepo;

	private RunSummaryRepository runSummaryRepository;

	private EventResultDetailsRepository eventResultDetailsRepository;
	
	private static final int totalCreditsForEvent = 5000;
	private EventResultDetails eventResultDetails;
	private List<EventResultDetails> eventResultDetailsList;
	private List<RunSummary> runSummaryList;
	private List<RunDetails> runDetailsList;
	private RunDetails runDetailsObj;
	private RunSummary runSummary;
	
	
	public static final Logger logger = LoggerFactory.getLogger(UpdateCreditsFork.class);

	public UpdateCreditsFork(EventResultDetails eventResultDetails, RunDetails runDetailsObj, RunSummary existingRunSummary, List<EventResultDetails> eventResultDetailsList,
			List<RunSummary> runSummaryList, List<RunDetails> runDetailsList,EventResultDetailsRepository eventResultDetailsRepository, RunDetailsRepository runDetailsRepo,
			RunSummaryRepository runSummaryRepository) {
		this.eventResultDetails = eventResultDetails;
		this.eventResultDetailsList = eventResultDetailsList;
		this.runSummaryList = runSummaryList;
		this.runDetailsList = runDetailsList;
		this.runSummary=existingRunSummary;
		this.runDetailsObj =runDetailsObj;
		this.eventResultDetailsRepository = eventResultDetailsRepository;
		this.runDetailsRepo = runDetailsRepo;
		this.runSummaryRepository = runSummaryRepository;
	}

	
	//TODO Compute Method should only hold logic to update credits for one user. The part to create sub tasks and to submit should be out of the worker
	@Override
	protected void compute() {
		if (this.eventResultDetails == null) {
			List<UpdateCreditsFork> subtasks = new ArrayList<UpdateCreditsFork>();
			subtasks.addAll(createSubtasks());

			for (UpdateCreditsFork subtask : subtasks) {
				subtask.fork();
			}

		} else
			updateCredits( runDetailsObj, runSummary, eventResultDetailsList, eventResultDetails);
	}

	private Collection<? extends UpdateCreditsFork> createSubtasks() {

		List<UpdateCreditsFork> subtasks = new ArrayList<UpdateCreditsFork>();

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
			UpdateCreditsFork subtask = new UpdateCreditsFork(erd, runDetailsObj, existingRunSummary, eventResultDetailsList, runSummaryList,
					runDetailsList,eventResultDetailsRepository, runDetailsRepo, runSummaryRepository);
			subtasks.add(subtask);
		}

		return subtasks;
	}

	private void updateCredits( RunDetails runDetailsObj, RunSummary runSummary, 
			List<EventResultDetails> eventResultDetailsList, EventResultDetails eventResultDetailsObj) {


		logger.info(Thread.currentThread().getName() + "START - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId() + " at "
				+ new Date());
		Long userRank = eventResultDetailsObj.getUserRank();
		double multiplier = 0.6;
		Double runCreditsForUser = ((1 - multiplier) / (1 - Math.pow(multiplier, eventResultDetailsList.size()))
				* Math.pow(multiplier, (userRank - 1))) * totalCreditsForEvent;

		eventResultDetailsObj.setRunCredits(runCreditsForUser);

		/* Starting run details and run summary credit calculation */
		
		runDetailsObj.setRunCredits(runCreditsForUser);

		runSummary.setTotalCredits(runSummary.getTotalCredits() + runCreditsForUser);
		
		saveCredits();

		/* Ending run details and run summary credit calculation */
		logger.info(Thread.currentThread().getName() + "END - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId() + " at "
				+ new Date());
	}


	@Transactional
	private void saveCredits() {
		eventResultDetailsRepository.save(this.eventResultDetails);
		runSummaryRepository.save(this.runSummary);
		runDetailsRepo.save(this.runDetailsObj);
	}
	
	

}