package com.example.taskmanager.repository;

import com.example.taskmanager.TaskmanagerApplication;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TaskmanagerApplication.class)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void save_ShouldPersistTask() {
        // Arrange
        Task task = new Task("Test Task", "Description");

        // Act
        Task saved = taskRepository.save(task);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Test Task", saved.getTitle());
        assertEquals(TaskStatus.OPEN, saved.getStatus());
    }

    @Test
    void findById_WhenTaskExists_ShouldReturnTask() {
        // Arrange
        Task task = new Task("Test Task", "Description");
        Task saved = taskRepository.save(task);

        // Act
        Optional<Task> found = taskRepository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void findByStatus_ShouldReturnTasksWithMatchingStatus() {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        task2.changeStatus(TaskStatus.DONE);
        
        taskRepository.save(task1);
        taskRepository.save(task2);

        // Act
        List<Task> openTasks = taskRepository.findByStatus(TaskStatus.OPEN);
        List<Task> doneTasks = taskRepository.findByStatus(TaskStatus.DONE);

        // Assert
        assertEquals(1, openTasks.size());
        assertEquals(1, doneTasks.size());
        assertEquals(TaskStatus.OPEN, openTasks.get(0).getStatus());
        assertEquals(TaskStatus.DONE, doneTasks.get(0).getStatus());
    }

    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnMatchingTasks() {
        // Arrange
        taskRepository.save(new Task("Spring Boot Tutorial", "Description"));
        taskRepository.save(new Task("Java Tutorial", "Description"));
        taskRepository.save(new Task("REST API", "Description"));

        // Act
        List<Task> result = taskRepository.findByTitleContainingIgnoreCase("tutorial");

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void deleteById_ShouldRemoveTask() {
        // Arrange
        Task task = taskRepository.save(new Task("To Delete", "Description"));
        Long taskId = task.getId();

        // Act
        taskRepository.deleteById(taskId);

        // Assert
        assertFalse(taskRepository.findById(taskId).isPresent());
    }
}
