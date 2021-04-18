package com.fitlers.schedulers;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fitlers.kafka.KafkaUtils;
import com.fitlers.repo.EventDetailsRepository;

@Profile("prod")
@Component
public class EventResultScheduler {

	private Map<String, KafkaStreams> mapOfStreams = new HashMap<String, KafkaStreams>();

	@Autowired
	private EventDetailsRepository eventDetailsRepo;

	@Autowired
	private KafkaUtils kafkaUtils;

	public static final Logger logger = LoggerFactory.getLogger(EventResultScheduler.class);

	// @Scheduled(cron = "0 * 13 * * ?")
	@Scheduled(fixedDelay = 30000)
	public void scheduledTask() {
		logger.info("Running Event Scheduler");

		// Create New Streams for today's events
		LocalDate currentDate = LocalDate.now();

		eventDetailsRepo.findAllEligibleEvents().stream().filter(event -> {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(event.getEventStartDate());
			LocalDate eventStartDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));
			return eventStartDate.isEqual(currentDate);
		}).forEach(event -> {
			String topicName = "EVENT_RUN_SUBMISSION_" + event.getEventId();
			if (!kafkaUtils.getAllExistingTopics().contains(topicName)) {
				kafkaUtils.createNewKafkaTopic(topicName);
				logger.info("Created New Kafka Topic " + topicName);
			}
			if (!mapOfStreams.containsKey(event.getEventId().toString())) {
				mapOfStreams.put(event.getEventId().toString(),
						kafkaUtils.createAndStartNewStream(topicName, event.getEventId()));
				logger.info("Created New Kafka Stream " + topicName);
			}
		});

		// Close Streams for past events
		eventDetailsRepo.findAll().stream().filter(event -> {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(event.getEventStartDate());
			LocalDate eventEndDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));
			return eventEndDate.isBefore(currentDate);
		}).forEach(event -> {
			if (mapOfStreams.containsKey(event.getEventId().toString())) {
				mapOfStreams.get(event.getEventId().toString()).close();
				mapOfStreams.remove(event.getEventId().toString());
				logger.info("Closed New Kafka Stream for event " + event.getEventId());
			}
		});
		logger.info("Running Event Scheduler Completed");
	}

}
