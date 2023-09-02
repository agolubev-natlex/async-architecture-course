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

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CREATED_TOPIC, id = "task-created-1")
    public void onTaskCreatedEvent(@Payload String message) {
        log.info("TaskCreatedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_UPDATED_TOPIC,id = "task-updated-1")
    public void onTaskUpdatedEvent(@Payload String message) {
        log.info("TaskUpdatedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CLOSED_TOPIC, id = "task-closed-1")
    public void onTaskClosedEvent(@Payload String message) {
        log.info("TaskClosedEvent received, {}", message);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_RESHUFFLED_TOPIC, id = "task-reshuffled-1")
    public void onTaskReshuffledEvent(@Payload String message) {
        log.info("TaskReshuffledEvent received, {}", message);
    }
}
