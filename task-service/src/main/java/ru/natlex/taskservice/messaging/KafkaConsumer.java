package ru.natlex.taskservice.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topics.users-topic}")
    public void consume(@Payload String message) {
        System.out.println("consumed");
    }
}
