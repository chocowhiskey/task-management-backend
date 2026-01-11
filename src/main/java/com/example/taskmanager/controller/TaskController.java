package com.example.taskmanager.controller;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /api/tasks - Neue Task erstellen
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String description = request.get("description");
        
        Task task = taskService.createTask(title, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // GET /api/tasks - Alle Tasks abrufen
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status) {
        
        List<Task> tasks;
        if (status != null) {
            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }
        
        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/{id} - Einzelne Task abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/tasks/{id} - Task aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        
        String title = (String) updates.get("title");
        String description = (String) updates.get("description");
        TaskStatus status = updates.containsKey("status") 
                ? TaskStatus.valueOf((String) updates.get("status")) 
                : null;
        
        Task updatedTask = taskService.updateTask(id, title, description, status);
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE /api/tasks/{id} - Task löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}