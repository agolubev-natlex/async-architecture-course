package ru.natlex.commons.config.utils;

public final class KafkaTopics {
    private KafkaTopics() {

    }

    public static final String TASK_SERVICE_TASK_CREATED_TOPIC = "task-service.task.created";
    public static final String TASK_SERVICE_TASK_UPDATED_TOPIC = "task-service.task.updated";
    public static final String TASK_SERVICE_TASK_CLOSED_TOPIC = "task-service.task.closed";
    public static final String TASK_SERVICE_TASK_RESHUFFLED_TOPIC = "task-service.task.reshuffled";


    public static final String AUTH_SERVICE_USER_CREATED_TOPIC = "auth-service.user.created";
}
