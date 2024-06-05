package org.homework;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.homework.util.PropertiesHelper;

import static org.homework.util.PropertiesHelper.TOPIC_ONE;
import static org.homework.util.PropertiesHelper.TOPIC_TWO;

@Slf4j
public class Producer {

    public static void main(String[] args) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(PropertiesHelper.getProperties())) {
            producer.initTransactions();

            producer.beginTransaction();
            sendMessageInLoop(producer, 5, "transactional");
            producer.commitTransaction();

            producer.beginTransaction();
            sendMessageInLoop(producer, 2, "not transactional");
            producer.abortTransaction();

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private static void sendMessageInLoop(KafkaProducer<String, String> producer, int messages, String isTransactional) {
        for (int i = 0; i < messages; i++) {
            sendMessage(producer, TOPIC_ONE, String.valueOf(i), String.format("%s %d", isTransactional, i));
            sendMessage(producer, TOPIC_TWO, String.valueOf(i), String.format("%s %d", isTransactional, i));
        }
    }

    private static void sendMessage(KafkaProducer<String, String> producer, String topic, String key, String value) {
        var record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
        log.info("Send message to topic: {} - key: {} - value: {}", record.topic(), record.key(), record.value());
    }
}
