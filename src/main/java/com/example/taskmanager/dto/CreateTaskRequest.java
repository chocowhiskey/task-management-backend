package com.example.taskmanager.dto;

public class CreateTaskRequest {
    
    private String title;
    private String description;

    // Default Constructor (für Jackson - JSON Deserialisierung)
    public CreateTaskRequest() {
    }

    public CreateTaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // Setters (für Jackson)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}