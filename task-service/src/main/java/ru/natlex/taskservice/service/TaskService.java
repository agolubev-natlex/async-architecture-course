package ru.natlex.taskservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.natlex.taskservice.entity.Task;
import ru.natlex.taskservice.entity.UserPublicAccount;
import ru.natlex.taskservice.messaging.KafkaProducer;
import ru.natlex.taskservice.repository.TaskRepository;

import java.security.SecureRandom;
import java.util.List;

@RequiredArgsConstructor
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
        kafkaProducer.publishMessage(task);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void closeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
        task.setStatus(Task.TaskStatus.CLOSE);
        taskRepository.save(task);
        kafkaProducer.publishMessage(task);
    }

    public void updateTask(Long id, String name, String description, String userPublicId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
        task.setName(name);
        task.setDescription(description);
        task.setUserPublicAccountId(userPublicId);
        taskRepository.save(task);
        kafkaProducer.publishMessage(task);
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
        kafkaProducer.publishMessage(openedTasks);
    }
}
