package com.fitlers.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import com.fitlers.entities.EventDetails;
import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.repo.EventResultDetailsRepository;

public class EventRunsProcessor implements Processor<String, RunDetails> {

	private String stateStoreName;
	private ProcessorContext context;
	private KeyValueStore<String, EventResultDetails> kvStore;
	private EventResultDetailsRepository eventResultDetailsRepo;
	private EventDetails eventDetails;

	public EventRunsProcessor(String stateStoreName, EventResultDetailsRepository eventResultDetailsRepo,
			EventDetails eventDetails) {
		this.stateStoreName = stateStoreName;
		this.eventResultDetailsRepo = eventResultDetailsRepo;
		this.eventDetails = eventDetails;
	}

	@Override
	public void init(ProcessorContext context) {
		this.context = context;
		this.kvStore = (KeyValueStore<String, EventResultDetails>) context.getStateStore(stateStoreName);

		this.context.schedule(Duration.ofMinutes(3), PunctuationType.WALL_CLOCK_TIME, (timestamp) -> {
			Map<String, EventResultDetails> resultMapForEvent = new HashMap<String, EventResultDetails>();
			KeyValueIterator<String, EventResultDetails> iter = kvStore.all();
			while (iter.hasNext()) {
				KeyValue<String, EventResultDetails> keyValue = iter.next();
				resultMapForEvent.put(keyValue.key, keyValue.value);
			}
			iter.close();
			eventResultDetailsRepo.saveAll(resultMapForEvent.values());
		});
	}

	@Override
	public void process(String key, RunDetails value) {
		EventResultDetails eventResultDetails = new EventResultDetails();
		eventResultDetails.setEventId(value.getEventId());
		eventResultDetails.setRunId(value.getRunId());
		eventResultDetails.setUserId(value.getUserId());
		eventResultDetails.setRunTotalTime(value.getRunTotalTime());
		eventResultDetails.setRunDistance(value.getRunDistance());
		kvStore.put(key, eventResultDetails);

		TreeMap<Long, List<EventResultDetails>> sortedMapOfTimeAndEventResults = new TreeMap<Long, List<EventResultDetails>>();
		double eventMetricValue = Double.parseDouble(this.eventDetails.getEventMetricValue());

		// Iterate over the state store and add all values to TreeMap
		KeyValueIterator<String, EventResultDetails> iter = kvStore.all();
		while (iter.hasNext()) {
			KeyValue<String, EventResultDetails> keyValue = iter.next();
			long userTotalTime = Long.valueOf(keyValue.value.getRunTotalTime());
			double userTotalDistance = keyValue.value.getRunDistance();
			if (userTotalDistance > (eventMetricValue * 1000)) {
				userTotalTime = (long) ((userTotalTime / userTotalDistance) * (eventMetricValue*1000));
			}
			sortedMapOfTimeAndEventResults.putIfAbsent(userTotalTime, new ArrayList<EventResultDetails>());
			sortedMapOfTimeAndEventResults.get(userTotalTime).add(keyValue.value);
		}
		iter.close();

		// Iterate over the sorted tree map and populate the calculated rank
		Iterator<Entry<Long, List<EventResultDetails>>> iter1 = sortedMapOfTimeAndEventResults.entrySet().iterator();
		long userRank = 0;
		while (iter1.hasNext()) {
			List<EventResultDetails> listOfEventResults = iter1.next().getValue();
			for (EventResultDetails eventResult : listOfEventResults) {
				userRank++;
				eventResult.setUserRank(userRank);
				kvStore.put(eventResult.getUserId(),eventResult);
			}
		}
	}

	@Override
	public void close() {

	}

}
