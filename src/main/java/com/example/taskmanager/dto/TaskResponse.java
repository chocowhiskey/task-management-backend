package com.example.taskmanager.dto;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {
    
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor der aus Task-Entity erstellt
    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }

    // Default Constructor
    public TaskResponse() {
    }

    // Getters (Jackson braucht diese für JSON-Serialisierung)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}