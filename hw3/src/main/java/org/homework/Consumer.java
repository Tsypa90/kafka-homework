package org.homework;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.homework.util.PropertiesHelper;

import java.time.Duration;
import java.util.List;

import static org.homework.util.PropertiesHelper.TOPIC_ONE;
import static org.homework.util.PropertiesHelper.TOPIC_TWO;

@Slf4j
public class Consumer {
    public static void main(String[] args) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(PropertiesHelper.getProperties()); consumer) {
            consumer.subscribe(List.of(TOPIC_ONE, TOPIC_TWO));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Receive message from topic: {} - key: {} - value: {}", record.topic(), record.key(), record.value());
                }
            }
        } catch (Exception exception) {
            log.warn(exception.getMessage());
        }
    }
}
