package ru.natlex.taskservice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(
            id = "group-1",
            topics = "${kafka.topics.users-topic}")
    public void consume(@Payload String message) {
        log.info("message consumed: {}", message);
    }
}
