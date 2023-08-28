package ru.natlex.accountingservice.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.natlex.commons.config.utils.KafkaTopics;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaskListener {

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CREATED_TOPIC, id = "1")
    public void onTaskCreatedEvent(@Payload String message) {
        log.info("TaskCreatedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_UPDATED_TOPIC,id = "2")
    public void onTaskUpdatedEvent(@Payload String message) {
        log.info("TaskUpdatedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CLOSED_TOPIC, id = "3")
    public void onTaskClosedEvent(@Payload String message) {
        log.info("TaskClosedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_RESHUFFLED_TOPIC, id = "4")
    public void onTaskReshuffledEvent(@Payload String message) {
        log.info("TaskReshuffledEvent received, {}", message);
    }
}
