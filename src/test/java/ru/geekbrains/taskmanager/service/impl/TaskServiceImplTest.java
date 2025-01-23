package ru.geekbrains.taskmanager.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.exception.TaskNotFoundException;
import ru.geekbrains.taskmanager.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    public TaskServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById_Success() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("New Description");
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Task");

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).existsById(1L);
    }

    @Test
    public void testGetTasksByStatus() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.TODO);

        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(List.of(task));

        List<Task> tasks = taskService.getTasksByStatus(TaskStatus.TODO);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
        verify(taskRepository, times(1)).findByStatus(TaskStatus.TODO);
    }

    @Test
    public void testGetTasksByDueDate() {
        LocalDate dueDate = LocalDate.now().plusDays(5);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDueDate(dueDate);

        when(taskRepository.findByDueDate(dueDate)).thenReturn(List.of(task));

        List<Task> tasks = taskService.getTasksByDueDate(dueDate);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(dueDate, tasks.get(0).getDueDate());
        verify(taskRepository, times(1)).findByDueDate(dueDate);
    }

    @Test
    public void testSearchTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Search Task");
        task.setDescription("Task Description");

        when(taskRepository.searchByKeyword("Search")).thenReturn(List.of(task));

        List<Task> tasks = taskService.searchTasks("Search");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getTitle().contains("Search"));
        verify(taskRepository, times(1)).searchByKeyword("Search");
    }
}
