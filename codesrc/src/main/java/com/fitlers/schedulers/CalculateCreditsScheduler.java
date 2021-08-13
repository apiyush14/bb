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
	private EventDetailsRepository eventDetailsRepository;
	
	@Autowired
	private RunDetailsRepository runDetailsRepo;
	
	@Autowired
	private RunSummaryRepository runSummaryRepository;
	
	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepository;

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
    				 
    		        ForkJoinPool pool = new ForkJoinPool(100);
    		        System.out.println("ProcessStarted at :"+new Date());
    		    
    		        pool.invoke(updateCreditsForkObj);
    		        while( !pool.isQuiescent());
    		       
    		        System.out.println("ProcessCompleted at :"+new Date());
    			 completedEvent.setIsCreditCalculated("Y");
    			 
			}
    			
       			saveAll(completedEvent); // Commit all data in same transaction
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
        public void saveAll(EventDetails completedEvent){
			
			SaveCreditsFork saveCreditsFork = new SaveCreditsFork(true, eventResultDetailsList,runSummaryList,runDetailsList, eventResultDetailsRepository, runDetailsRepo, runSummaryRepository);
			 
	        ForkJoinPool pool = new ForkJoinPool(20);
	        System.out.println("Save Process Started at :"+new Date());
	    
	        pool.invoke(saveCreditsFork);
	        while( !pool.isQuiescent());
        	if(completedEvent!=null) eventDetailsRepository.save(completedEvent);
        	System.out.println("Save Process Ended at :"+new Date());
        	
        	}



		@Scheduled(cron = "* 0/30 * * * ?")
		public void scheduledTaskForClass(){
                  
					   eventResultDetailsList = new ArrayList();
                      
                       completedEventsList=new ArrayList();
           			completedEventsList = eventDetailsRepository.findAllCompletedEvents();
           			if(!completedEventsList.isEmpty()) {
           			  calculateCreditsForUserRun();
                     System.out.println(Thread.currentThread().getName()+" The Task executed at "+ new Date());
                   
           			}
           		
                     
                      }

   }

