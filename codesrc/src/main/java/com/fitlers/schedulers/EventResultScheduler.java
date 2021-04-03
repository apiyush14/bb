package com.fitlers.schedulers;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fitlers.core.kafka.KafkaUtils;
import com.fitlers.repo.EventDetailsRepository;

@Profile("prod")
@Component
public class EventResultScheduler {

	private Map<String, KafkaStreams> mapOfStreams = new HashMap<String, KafkaStreams>();

	@Autowired
	private EventDetailsRepository eventDetailsRepo;

	@Autowired
	private KafkaUtils kafkaUtils;

	@Scheduled(cron = "0 * 13 * * ?")
	public void scheduledTask() {
		System.out.println("========Running Cron Job================");

		// Create New Streams for today's events
		LocalDate currentDate = LocalDate.now();

		eventDetailsRepo.findAll().parallelStream().filter(event -> {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(event.getEventStartDate());
			LocalDate eventStartDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));
			return eventStartDate.isEqual(currentDate);
		}).forEach(event -> {
			String topicName = "EVENT_RUN_SUBMISSION_" + event.getEventId();
			if (!kafkaUtils.getAllExistingTopics().contains(topicName)) {
				kafkaUtils.createNewKafkaTopic(topicName);
				if (!mapOfStreams.containsKey(event.getEventId().toString())) {
					mapOfStreams.put(event.getEventId().toString(),
							kafkaUtils.createAndStartNewStream(topicName, event.getEventId()));
				}
			}
		});

		// Close Streams for past events
		eventDetailsRepo.findAll().parallelStream().filter(event -> {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(event.getEventStartDate());
			LocalDate eventEndDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH));
			return eventEndDate.isBefore(currentDate);
		}).forEach(event -> {
			if (mapOfStreams.containsKey(event.getEventId().toString())) {
				mapOfStreams.get(event.getEventId().toString()).close();
				mapOfStreams.remove(event.getEventId().toString());
			}
		});

	}

}
