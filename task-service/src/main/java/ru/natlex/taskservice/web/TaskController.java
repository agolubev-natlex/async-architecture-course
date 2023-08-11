package ru.natlex.taskservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping()
    public String[] getTasks() {
        return new String[] { "Task 1", "Task 2", "Task 3" };
    }
}
