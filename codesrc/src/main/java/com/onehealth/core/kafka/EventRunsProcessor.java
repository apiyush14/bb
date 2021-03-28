package com.onehealth.core.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import com.onehealth.entities.EventResultDetails;
import com.onehealth.entities.RunDetails;
import com.onehealth.repo.EventResultDetailsRepository;

public class EventRunsProcessor implements Processor<String, RunDetails> {

	private String stateStoreName;
	private ProcessorContext context;
	private KeyValueStore<String, EventResultDetails> kvStore;
	private EventResultDetailsRepository eventResultDetailsRepo;

	public EventRunsProcessor(String stateStoreName, EventResultDetailsRepository eventResultDetailsRepo) {
		this.stateStoreName = stateStoreName;
		this.eventResultDetailsRepo = eventResultDetailsRepo;
	}

	@Override
	public void init(ProcessorContext context) {
		this.context = context;
		this.kvStore = (KeyValueStore<String, EventResultDetails>) context.getStateStore(stateStoreName);
		/*
		 * KeyValueIterator<String, EventResultDetails> iter1=kvStore.all();
		 * while(iter1.hasNext()) { KeyValue<String, EventResultDetails>
		 * keyValue=iter1.next(); kvStore.delete(keyValue.key); } iter1.close();
		 */

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
		kvStore.put(key, eventResultDetails);

		Map<String, EventResultDetails> resultMapForEvent = new HashMap<String, EventResultDetails>();
		KeyValueIterator<String, EventResultDetails> iter = kvStore.all();
		while (iter.hasNext()) {
			KeyValue<String, EventResultDetails> keyValue = iter.next();
			resultMapForEvent.put(keyValue.key, keyValue.value);
		}
		iter.close();

		List<String> listOfUsers = new ArrayList<String>();

		resultMapForEvent.entrySet().stream().sorted((e1,
				e2) -> Long.valueOf(e2.getValue().getRunTotalTime()) - Long.valueOf(e1.getValue().getRunTotalTime()) > 0
						? 1
						: -1)
				.map((e) -> {
					listOfUsers.add(e.getKey());
					e.getValue().setUserRank((long) listOfUsers.size());
					return e;
				}).parallel().forEach(e -> {
					kvStore.put(e.getKey(), e.getValue());
				});

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
