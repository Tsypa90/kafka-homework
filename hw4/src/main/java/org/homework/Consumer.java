package org.homework;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.time.Duration;
import java.util.Map;

@Slf4j
public class Consumer {
    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        builder.<String, String>stream("events")
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5)))
                .count();

        var topology = builder.build();

        log.info("{}", topology.describe());

        try (KafkaStreams kafkaStreams = new KafkaStreams(topology, new StreamsConfig(Map.of(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                StreamsConfig.APPLICATION_ID_CONFIG, "hw4")))) {
            kafkaStreams.start();
            log.info("Consumer started");

            Thread.sleep(15000);

            log.info("Consumer stopped");

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
}
