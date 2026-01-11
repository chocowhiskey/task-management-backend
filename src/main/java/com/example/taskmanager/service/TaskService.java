package com.example.taskmanager.service;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmanager.exception.TaskNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor Injection (Best Practice!)
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Neue Task erstellen
    public Task createTask(String title, String description) {
        Task task = new Task(title, description);
        return taskRepository.save(task);
    }

    // Alle Tasks abrufen
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Task by ID finden
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Tasks nach Status filtern
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    // Task aktualisieren
    public Task updateTask(Long id, String title, String description, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        
        if (title != null) {
            task.updateTitle(title);
        }
        if (description != null) {
            task.updateDescription(description);
        }
        if (status != null) {
            task.changeStatus(status);
        }
        
        return taskRepository.save(task);
    }

    // Task löschen
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}