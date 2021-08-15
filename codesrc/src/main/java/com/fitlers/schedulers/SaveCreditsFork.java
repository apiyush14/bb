package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;

import org.springframework.transaction.annotation.Transactional;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunSummary;
import com.fitlers.repo.EventResultDetailsRepository;
import com.fitlers.repo.RunDetailsRepository;
import com.fitlers.repo.RunSummaryRepository;

public class SaveCreditsFork extends RecursiveAction {

	private RunDetailsRepository runDetailsRepo;

	private RunSummaryRepository runSummaryRepository;

	private EventResultDetailsRepository eventResultDetailsRepository;

	private List<EventResultDetails> eventResultDetailsList;
	private List<RunSummary> runSummaryList;
	private List<RunDetails> runDetailsList;
	private boolean isNew = false;
	private EventResultDetails eventResultDetails;
	private RunSummary runSummary;
	private RunDetails runDetails;

	public SaveCreditsFork(boolean isNew, List<EventResultDetails> eventResultDetailsList,
			List<RunSummary> runSummaryList, List<RunDetails> runDetailsList,
			EventResultDetailsRepository eventResultDetailsRepository, RunDetailsRepository runDetailsRepo,
			RunSummaryRepository runSummaryRepository) {
		this.eventResultDetailsList = eventResultDetailsList;
		this.runSummaryList = runSummaryList;
		this.runDetailsList = runDetailsList;
		this.isNew = isNew;
		this.eventResultDetailsRepository = eventResultDetailsRepository;
		this.runDetailsRepo = runDetailsRepo;
		this.runSummaryRepository = runSummaryRepository;
	}

	public SaveCreditsFork(EventResultDetails eventResultDetails, RunSummary runSummary, RunDetails runDetails,
			EventResultDetailsRepository eventResultDetailsRepository, RunDetailsRepository runDetailsRepo,
			RunSummaryRepository runSummaryRepository) {
		this.eventResultDetails = eventResultDetails;
		this.runSummary = runSummary;
		this.runDetails = runDetails;
		this.eventResultDetailsRepository = eventResultDetailsRepository;
		this.runDetailsRepo = runDetailsRepo;
		this.runSummaryRepository = runSummaryRepository;
	}

	@Override
	protected void compute() {

		if (this.isNew) {
			List<SaveCreditsFork> subtasks = new ArrayList<SaveCreditsFork>();
			subtasks.addAll(createSubtasks());

			for (SaveCreditsFork subtask : subtasks) {
				subtask.fork();
			}

		} else
			saveCredits();

	}

	@Transactional
	private void saveCredits() {
		System.out.println("Save Started for User :" + eventResultDetails.getUserId() + " at " + new Date());
		eventResultDetailsRepository.save(this.eventResultDetails);
		runSummaryRepository.save(this.runSummary);
		runDetailsRepo.save(this.runDetails);
		System.out.println("Save Completed for User :" + eventResultDetails.getUserId() + " at " + new Date());
	}

	private Collection<? extends SaveCreditsFork> createSubtasks() {

		List<SaveCreditsFork> subtasks = new ArrayList<SaveCreditsFork>();

		for (EventResultDetails erd : this.eventResultDetailsList) {

			Optional<RunSummary> runSummary = runSummaryList.stream()
					.filter(runSummaryList -> runSummaryList.getUserId().equals(erd.getUserId())).findFirst();
			Optional<RunDetails> runDetails = runDetailsList.stream()
					.filter(runDetailsList -> runDetailsList.getUserId().equals(erd.getUserId())).findFirst();
			SaveCreditsFork subtask = new SaveCreditsFork(erd, runSummary.get(), runDetails.get(),
					eventResultDetailsRepository, runDetailsRepo, runSummaryRepository);
			subtasks.add(subtask);
		}

		return subtasks;
	}

}