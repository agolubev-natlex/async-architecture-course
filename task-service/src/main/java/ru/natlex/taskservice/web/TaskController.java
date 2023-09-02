package ru.natlex.taskservice.web;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.natlex.taskservice.entity.Task;
import ru.natlex.taskservice.service.TaskService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks/")
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('POPUG')")
    @GetMapping("my")
    public List<Task> getMyTasks() {
        String userPublicId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskService.getTasksByUser(userPublicId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("by-user/{userPublicId}")
    public List<Task> getMyTasks(@PathVariable String userPublicId) {
        return taskService.getTasksByUser(userPublicId);
    }

    @PostMapping
    public void createTask(@RequestBody @Validated TaskRequestBodyDto dto) {
        Task task = new Task();
        task.setName(dto.name);
        task.setDescription(dto.description);
        taskService.createTask(task);
    }

    @PostMapping("every-day-im-shuffling")
    public void shuffleTasks() {
        taskService.shuffleTasks();
    }

    @PatchMapping("{id}")
    public void updateTask(@PathVariable Long id, @RequestBody @Validated TaskRequestBodyDto dto) {
        taskService.updateTask(id, dto.name, dto.description);
    }

    @PutMapping("{id}")
    public void closeTask(@PathVariable Long id) {
        taskService.closeTask(id);
    }

    record TaskRequestBodyDto(@NotNull String name, String description) {

    }
}
