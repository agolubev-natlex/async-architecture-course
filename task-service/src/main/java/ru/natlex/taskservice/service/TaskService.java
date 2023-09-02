package ru.natlex.taskservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.natlex.commons.config.utils.KafkaTopics;
import ru.natlex.taskservice.entity.Task;
import ru.natlex.taskservice.entity.UserPublicAccount;
import ru.natlex.taskservice.messaging.KafkaProducer;
import ru.natlex.taskservice.repository.TaskRepository;

import java.security.SecureRandom;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final TaskRepository taskRepository;
    private final UserPublicAccountService userPublicAccountService;
    private final KafkaProducer kafkaProducer;

    public void createTask(Task task) {
        task.setWithdraw(-(secureRandom.nextInt(10) + 10));
        task.setAward(secureRandom.nextInt(20) + 20);
        taskRepository.save(task);
        kafkaProducer.publishMessage(KafkaTopics.TASK_SERVICE_TASK_CREATED_TOPIC, task);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void closeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
        task.setStatus(Task.TaskStatus.CLOSE);
        taskRepository.save(task);
        kafkaProducer.publishMessage(KafkaTopics.TASK_SERVICE_TASK_CLOSED_TOPIC, task);
    }

    public void updateTask(Long id, String name, String description) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
        task.setName(name);
        task.setDescription(description);
        taskRepository.save(task);
        kafkaProducer.publishMessage(KafkaTopics.TASK_SERVICE_TASK_UPDATED_TOPIC, task);
    }

    @Transactional
    public void shuffleTasks() {
        List<Task> openedTasks = taskRepository.findAllByStatus(Task.TaskStatus.OPEN);
        List<String> allAccounts = userPublicAccountService.getAll()
                .stream()
                .map(UserPublicAccount::getPublicId)
                .toList();

        if (allAccounts.isEmpty()) {
            return;
        }

        for (Task openedTask : openedTasks) {
            openedTask.setUserPublicAccountId(allAccounts.get(new SecureRandom().nextInt(allAccounts.size())));
        }
        taskRepository.saveAll(openedTasks);
        kafkaProducer.publishMessage(KafkaTopics.TASK_SERVICE_TASK_RESHUFFLED_TOPIC, openedTasks);
    }

    public List<Task> getTasksByUser(String userPublicId) {
        return taskRepository.findAllByUserPublicAccountIdOrderByIdDesc(userPublicId);
    }
}
