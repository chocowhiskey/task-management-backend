package com.example.taskmanager.repository;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Spring generiert die Implementierung automatisch!
    List<Task> findByStatus(TaskStatus status);
    
    List<Task> findByTitleContainingIgnoreCase(String title);
}