package com.example.taskmanager.controller;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;
import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /api/tasks
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request.getTitle(), request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResponse(task));
    }

    // GET /api/tasks
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) TaskStatus status) {
        
        List<Task> tasks;
        if (status != null) {
            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }
        
        List<TaskResponse> response = tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(new TaskResponse(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {
        
        Task updatedTask = taskService.updateTask(
                id, 
                request.getTitle(), 
                request.getDescription(), 
                request.getStatus()
        );
        
        return ResponseEntity.ok(new TaskResponse(updatedTask));
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}