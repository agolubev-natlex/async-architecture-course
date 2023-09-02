package ru.natlex.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.natlex.taskservice.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByStatus(Task.TaskStatus status);

    List<Task> findAllByUserPublicAccountIdOrderByIdDesc(String userPublicAccountId);
}
