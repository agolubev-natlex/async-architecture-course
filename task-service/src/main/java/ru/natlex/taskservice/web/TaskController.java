package ru.natlex.taskservice.web;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.natlex.taskservice.entity.Task;
import ru.natlex.taskservice.service.TaskService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    @PostMapping
    public void createTask(@RequestBody @Validated TaskRequestBodyDto dto) {
        Task task = new Task();
        task.setName(dto.name);
        task.setDescription(dto.description);
        task.setUserPublicAccountId(dto.userPublicId);
        taskService.createTask(task);
    }

    @PostMapping("/every-day-im-shuffling")
    public void shuffleTasks() {
        taskService.shuffleTasks();
    }

    @PatchMapping("{id}")
    public void updateTask(@PathVariable Long id, @RequestBody @Validated TaskRequestBodyDto dto) {
        taskService.updateTask(id, dto.name, dto.description, dto.userPublicId);
    }

    @PutMapping("{id}")
    public void closeTask(@PathVariable Long id) {
        taskService.closeTask(id);
    }

    record TaskRequestBodyDto(@NotNull String name, String description, String userPublicId) {

    }
}
