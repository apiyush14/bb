package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunSummary;

public class UpdateCreditsFork extends RecursiveAction {

	private static final int totalCreditsForEvent = 5000;
	private EventResultDetails eventResultDetails;
	private List<EventResultDetails> eventResultDetailsList;
	private List<RunSummary> runSummaryList;
	private List<RunDetails> runDetailsList;

	public UpdateCreditsFork(EventResultDetails eventResultDetails, List<EventResultDetails> eventResultDetailsList,
			List<RunSummary> runSummaryList, List<RunDetails> runDetailsList) {
		this.eventResultDetails = eventResultDetails;
		this.eventResultDetailsList = eventResultDetailsList;
		this.runSummaryList = runSummaryList;
		this.runDetailsList = runDetailsList;

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
			updateCredits(runDetailsList, runSummaryList, eventResultDetailsList, eventResultDetails);
	}

	private Collection<? extends UpdateCreditsFork> createSubtasks() {

		List<UpdateCreditsFork> subtasks = new ArrayList<UpdateCreditsFork>();

		for (EventResultDetails erd : this.eventResultDetailsList) {

			UpdateCreditsFork subtask = new UpdateCreditsFork(erd, eventResultDetailsList, runSummaryList,
					runDetailsList);
			subtasks.add(subtask);
		}

		return subtasks;
	}

	private void updateCredits(List<RunDetails> runDetailsList2, List<RunSummary> runSummaryList2,
			List<EventResultDetails> eventResultDetailsList, EventResultDetails eventResultDetailsObj) {

		//TODO To use loggers
		System.out.println(Thread.currentThread().getName() + "START - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId() + " at "
				+ new Date());
		Long userRank = eventResultDetailsObj.getUserRank();
		double multiplier = 0.6;
		Double runCreditsForUser = ((1 - multiplier) / (1 - Math.pow(multiplier, eventResultDetailsList.size()))
				* Math.pow(multiplier, (userRank - 1))) * totalCreditsForEvent;

		eventResultDetailsObj.setRunCredits(runCreditsForUser);

		/* Starting run details and run summary credit calculation */

		//TODO All this data can be fetched out before submitting to worker and then submitted to the worker 
		Optional<RunDetails> runDetails = runDetailsList2.stream()
				.filter(runDet -> (runDet.getUserId().equals(eventResultDetailsObj.getUserId())
						&& runDet.getRunId().equals(eventResultDetailsObj.getRunId())))
				.findFirst();
		if (runDetails.isPresent()) {
			runDetails.get().setRunCredits(runCreditsForUser);
		}

		//TODO All this data can be fetched out before submitting to worker and then submitted to the worker
		Optional<RunSummary> existingRunSummaryOptional = runSummaryList2.stream()
				.filter(runSum -> (runSum.getUserId().equals(eventResultDetailsObj.getUserId()))).findFirst();
		if (existingRunSummaryOptional.isPresent()) {
			RunSummary existingRunSummary = existingRunSummaryOptional.get();
			existingRunSummary.setTotalCredits(existingRunSummary.getTotalCredits() + runCreditsForUser);
		}

		/* Ending run details and run summary credit calculation */
		System.out.println(Thread.currentThread().getName() + "END - Updating Credits for User ID - "
				+ eventResultDetailsObj.getUserId() + "for event id - " + eventResultDetailsObj.getEventId() + " at "
				+ new Date());
	}

}