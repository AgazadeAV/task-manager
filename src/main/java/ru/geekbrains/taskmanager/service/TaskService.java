package ru.geekbrains.taskmanager.service;

import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    List<Task> getTasksByStatus(TaskStatus status);

    List<Task> getTasksByDueDate(LocalDate dueDate);

    List<Task> searchTasks(String keyword);
}
