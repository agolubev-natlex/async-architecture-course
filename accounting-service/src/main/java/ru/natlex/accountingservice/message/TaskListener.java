package ru.natlex.accountingservice.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.natlex.accountingservice.entity.AuditLog;
import ru.natlex.accountingservice.service.AuditLogService;
import ru.natlex.commons.config.utils.KafkaTopics;

@Slf4j
@AllArgsConstructor
@Component
public class TaskListener {

    private final AuditLogService logService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CREATED_TOPIC, id = "task-created-1")
    public void onTaskCreatedEvent(@Payload String message) throws JsonProcessingException {
        log.info("TaskCreatedEvent received, {}", message);
        TaskCreatedMessage taskCreatedMessage = objectMapper.readValue(message, TaskCreatedMessage.class);
        AuditLog auditLog = new AuditLog();
        auditLog.setTaskId(taskCreatedMessage.taskId());
        auditLog.setCredit(taskCreatedMessage.withdraw());
        auditLog.setOperation("TASK_CREATED");
        auditLog.setUserPublicId(taskCreatedMessage.userPublicId());
        logService.create(auditLog);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_UPDATED_TOPIC,id = "task-updated-1")
    public void onTaskUpdatedEvent(@Payload String message) throws JsonProcessingException {
        TaskUpdatedMessage taskCreatedMessage = objectMapper.readValue(message, TaskUpdatedMessage.class);
        AuditLog auditLog = new AuditLog();
        auditLog.setTaskId(taskCreatedMessage.taskId());
        auditLog.setOperation("TASK_UPDATED");
        auditLog.setUserPublicId(taskCreatedMessage.userPublicId());
        logService.create(auditLog);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_CLOSED_TOPIC, id = "task-closed-1")
    public void onTaskClosedEvent(@Payload String message) throws JsonProcessingException {
        TaskClosedMessage taskCreatedMessage = objectMapper.readValue(message, TaskClosedMessage.class);
        AuditLog auditLog = new AuditLog();
        auditLog.setTaskId(taskCreatedMessage.taskId());
        auditLog.setDebit(taskCreatedMessage.award());
        auditLog.setOperation("TASK_CLOSED");
        auditLog.setUserPublicId(taskCreatedMessage.userPublicId());
        logService.create(auditLog);
    }

    @KafkaListener(topics = KafkaTopics.TASK_SERVICE_TASK_RESHUFFLED_TOPIC, id = "task-reshuffled-1")
    public void onTaskReshuffledEvent(@Payload String message) throws JsonProcessingException {
        TaskReshuffledEvent[] tasksReshuffledEvents = objectMapper.readValue(message, TaskReshuffledEvent[].class);
        for (TaskReshuffledEvent tasksReshuffledEvent : tasksReshuffledEvents) {
            AuditLog auditLog = new AuditLog();
            auditLog.setTaskId(tasksReshuffledEvent.taskId());
            auditLog.setDebit(tasksReshuffledEvent.award());
            auditLog.setOperation("TASK_RESHUFFLED");
            auditLog.setUserPublicId(tasksReshuffledEvent.userPublicId());
            logService.create(auditLog);
        }
    }

    record TaskCreatedMessage(@JsonProperty("id") Long taskId, @JsonProperty("userPublicAccountId") String userPublicId, Long withdraw) {

    }

    record TaskUpdatedMessage(@JsonProperty("id") Long taskId, @JsonProperty("userPublicAccountId") String userPublicId) {

    }


    record TaskClosedMessage(@JsonProperty("id") Long taskId, @JsonProperty("userPublicAccountId") String userPublicId, String status, Long award) {

    }

    record TaskReshuffledEvent(@JsonProperty("id") Long taskId, @JsonProperty("userPublicAccountId") String userPublicId, String status, Long award) {

    }
}
