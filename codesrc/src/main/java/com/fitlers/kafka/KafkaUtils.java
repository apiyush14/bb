package com.fitlers.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fitlers.entities.EventDetails;
import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.RunDetails;
import com.fitlers.repo.EventDetailsRepository;
import com.fitlers.repo.EventResultDetailsRepository;

@Profile("prod")
@Component
public class KafkaUtils {

	@Value("${kafkaBootstrapServer}")
	private String kafkaBootstrapServer;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private AdminClient adminClient;

	@Autowired
	private EventResultDetailsRepository eventResultDetailsRepo;

	@Autowired
	private EventDetailsRepository eventDetailsRepo;

	public void sendMessage(String topicName, Object msg) {
		ListenableFuture<SendResult<String, Object>> future;
		try {
			future = kafkaTemplate.send(topicName, msg);
			future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

				@Override
				public void onSuccess(SendResult<String, Object> result) {
					System.out.println(
							"Sent message=[" + msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
				}

				@Override
				public void onFailure(Throwable ex) {
					System.out.println("Unable to send message=[" + msg + "] due to : " + ex.getMessage());
				}
			});
		} catch (Exception e) {

		}
	}

	public Set<String> getAllExistingTopics() {
		try {
			return adminClient.listTopics().names().get();
		} catch (Exception e) {
			return new HashSet<String>();
		}
	}

	public void createNewKafkaTopic(String topicName) {
		NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
		List<NewTopic> newTopics = new ArrayList<NewTopic>();
		newTopics.add(newTopic);
		adminClient.createTopics(newTopics);
		//adminClient.close();
	}
	
	public void deleteKafkaTopic(String topicName) {
		List<String> topicsToBeDeletedList = new ArrayList<String>();
		topicsToBeDeletedList.add(topicName);
		adminClient.deleteTopics(topicsToBeDeletedList);
		adminClient.close();
	}

	public KafkaStreams createAndStartNewStream(String topicName, long eventId) {
		KafkaStreams streams = null;
		try {
			Properties props = new Properties();
			props.put(StreamsConfig.APPLICATION_ID_CONFIG, "APPLICATION_ID_"+eventId);
			props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.64:9092");
			props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

			EventDetails eventDetails = eventDetailsRepo.findById(eventId).get();

			Map<String, Object> serdeProps = new HashMap<>();
			final Serializer<RunDetails> runDetailsSerializer = new JsonPOJOSerializer<>();
			serdeProps.put("JsonPOJOClass", RunDetails.class);
			runDetailsSerializer.configure(serdeProps, false);

			final Deserializer<RunDetails> runDetailsDeserializer = new JsonPOJODeserializer<>();
			serdeProps.put("JsonPOJOClass", RunDetails.class);
			runDetailsDeserializer.configure(serdeProps, false);
			final Serde<RunDetails> runDetailsSerde = Serdes.serdeFrom(runDetailsSerializer, runDetailsDeserializer);

			final Serializer<EventResultDetails> eventResultDetailsSerializer = new JsonPOJOSerializer<>();
			serdeProps.put("JsonPOJOClass", EventResultDetails.class);
			eventResultDetailsSerializer.configure(serdeProps, false);

			final Deserializer<EventResultDetails> eventResultDetailsDeserializer = new JsonPOJODeserializer<>();
			serdeProps.put("JsonPOJOClass", EventResultDetails.class);
			eventResultDetailsDeserializer.configure(serdeProps, false);
			final Serde<EventResultDetails> eventResultDetailsSerde = Serdes.serdeFrom(eventResultDetailsSerializer,
					eventResultDetailsDeserializer);

			StreamsBuilder streamBuilder = new StreamsBuilder();
			StoreBuilder<KeyValueStore<String, EventResultDetails>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(
					Stores.persistentKeyValueStore("EVENT_RESULT_DETAILS" + eventId), Serdes.String(),
					eventResultDetailsSerde);
			streamBuilder.addStateStore(keyValueStoreBuilder);
			KStream<String, RunDetails> runsInput = streamBuilder.stream(topicName,
					Consumed.with(Serdes.String(), runDetailsSerde));

			runsInput.selectKey((k, v) -> v.getUserId())
					.process(() -> new EventRunsProcessor("EVENT_RESULT_DETAILS" + eventId, eventResultDetailsRepo,
							eventDetails), "EVENT_RESULT_DETAILS" + eventId);

			streams = new KafkaStreams(streamBuilder.build(), props);
			// streams.cleanUp();
			streams.start();
			Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
		} catch (Exception e) {
			throw e;
		}
		return streams;
	}

}
