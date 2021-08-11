package com.fitlers.schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledFuture;

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
public class CalculateCreditsScheduler  {

	

	@Autowired
	EventDetailsRepository eventDetailsRepository;
	
	@Autowired
	private RunDetailsRepository runDetailsRepo;
	
	@Autowired
	RunSummaryRepository runSummaryRepository;
	
	@Autowired
	EventResultDetailsRepository eventResultDetailsRepository;

        TaskScheduler taskScheduler;
        private List<RunSummary> runSummaryList;
        private List<RunDetails> runDetailsList;
        private List<EventResultDetails> eventResultDetailsList;;
        private List<EventDetails> completedEventsList;

		private void calculateCreditsForUserRun() {

		        
			for(EventDetails completedEvent:completedEventsList) {
    			EventResultDetails eventResultDetailsQueryObj = new EventResultDetails();
    			eventResultDetailsQueryObj.setEventId(completedEvent.getEventId());
    			Example<EventResultDetails> eventResultDetailsQueryExample = Example
    					.of(eventResultDetailsQueryObj);
    			
    			
    			
    			eventResultDetailsList = eventResultDetailsRepository.findAll(eventResultDetailsQueryExample);
    			if(eventResultDetailsList!=null && !eventResultDetailsList.isEmpty()) {
    				 runSummaryList = new ArrayList();
                     runDetailsList = new ArrayList();
    				
    				eventResultDetailsList.forEach(eventResultDetails-> populateRunDetailsList(eventResultDetails));
    				UpdateCreditsFork updateCreditsForkObj = new UpdateCreditsFork(null, eventResultDetailsList,runSummaryList,runDetailsList);
    				 
    		        ForkJoinPool pool = new ForkJoinPool();
    		        System.out.println("ProcessStarted at :"+new Date());
    		    
    		        pool.invoke(updateCreditsForkObj);
    		        while( !pool.isQuiescent());
    		       
    		        System.out.println("ProcessCompleted at :"+new Date());
    			 completedEvent.setIsCreditCalculated("Y");
    			 
			}
    			
       			saveAll(); // Commit all data in same transaction
			}
			
			
            
		}
        
        private void populateRunDetailsList(EventResultDetails eventResultDetails) {
        	RunDetailsId runDetailsId = new RunDetailsId(eventResultDetails.getRunId(), eventResultDetails.getUserId());
     		//runDetailsId.set

     		Optional<RunDetails> runDetails = runDetailsRepo.findById(runDetailsId);
     		if(runDetails.isPresent()) {
     			runDetailsList.add(runDetails.get());
     		}
     		
     		Optional<RunSummary> runSummary = runSummaryRepository.findById(eventResultDetails.getUserId());
     		if(runSummary.isPresent()) {
     			runSummaryList.add(runSummary.get());
     		}
}

		@Transactional
        public void saveAll(){
        	if(!completedEventsList.isEmpty()) eventDetailsRepository.saveAll(completedEventsList);
        	if(!eventResultDetailsList.isEmpty())  eventResultDetailsRepository.saveAll(eventResultDetailsList);
        	if(!runDetailsList.isEmpty())  runDetailsRepo.saveAll(runDetailsList);
        	if(!runSummaryList.isEmpty())  runSummaryRepository.saveAll(runSummaryList);
        	}



		@Scheduled(cron = "* 0/30 * * * ?")
		public void scheduledTaskForClass(){
                  
					   eventResultDetailsList = new ArrayList();
                      
                       completedEventsList=new ArrayList();
           			completedEventsList = eventDetailsRepository.findAllCompletedEvents();
           			if(!completedEventsList.isEmpty()) {
                     System.out.println(Thread.currentThread().getName()+" The Task2 executed at "+ new Date());
                   
           			}
           		  calculateCreditsForUserRun();
                     
                      }

   }

