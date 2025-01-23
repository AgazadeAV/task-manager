package ru.geekbrains.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.mapper.TaskMapper;
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.TaskServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TaskControllerTest {

    @Mock
    private TaskServiceImpl taskServiceImpl;

    @Mock
    private DataExportImportServiceImpl dataExportImportServiceImpl;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");

        when(taskServiceImpl.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        List<TaskDTO> tasks = taskController.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(taskServiceImpl, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");

        when(taskServiceImpl.getTaskById(1L)).thenReturn(task);
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);

        assertNotNull(response);
        assertEquals("Test Task", response.getBody().getTitle());
        verify(taskServiceImpl, times(1)).getTaskById(1L);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");

        when(taskMapper.mapToEntity(taskDTO)).thenReturn(task);
        when(taskServiceImpl.createTask(task)).thenReturn(task);
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        assertNotNull(response);
        assertEquals("New Task", response.getBody().getTitle());
        verify(taskServiceImpl, times(1)).createTask(task);
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setTitle("Updated Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");

        when(taskMapper.mapToEntity(taskDTO)).thenReturn(task);
        when(taskServiceImpl.updateTask(1L, task)).thenReturn(task);
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, taskDTO);

        assertNotNull(response);
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskServiceImpl, times(1)).updateTask(1L, task);
    }

    @Test
    public void testDeleteTask() {
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(taskServiceImpl, times(1)).deleteTask(1L);
    }

    @Test
    public void testGetTasksByStatus() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.TODO);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStatus("TODO");

        when(taskServiceImpl.getTasksByStatus(TaskStatus.TODO)).thenReturn(List.of(task));
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        List<TaskDTO> tasks = taskController.getTasksByStatus(TaskStatus.TODO);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("TODO", tasks.get(0).getStatus());
        verify(taskServiceImpl, times(1)).getTasksByStatus(TaskStatus.TODO);
    }

    @Test
    public void testGetTasksByDueDate() {
        LocalDate dueDate = LocalDate.now();
        Task task = new Task();
        task.setId(1L);
        task.setDueDate(dueDate);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDueDate(dueDate);

        when(taskServiceImpl.getTasksByDueDate(dueDate)).thenReturn(List.of(task));
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        List<TaskDTO> tasks = taskController.getTasksByDueDate(dueDate);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(dueDate, tasks.get(0).getDueDate());
        verify(taskServiceImpl, times(1)).getTasksByDueDate(dueDate);
    }

    @Test
    public void testSearchTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Search Task");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Search Task");

        when(taskServiceImpl.searchTasks("Search")).thenReturn(List.of(task));
        when(taskMapper.mapToDto(task)).thenReturn(taskDTO);

        List<TaskDTO> tasks = taskController.searchTasks("Search");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Search Task", tasks.get(0).getTitle());
        verify(taskServiceImpl, times(1)).searchTasks("Search");
    }

    @Test
    public void testExportTasks() throws IOException {
        ResponseEntity<String> response = taskController.exportTasks("tasks.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).exportTasks("tasks.json");
    }

    @Test
    public void testImportTasks() throws IOException {
        ResponseEntity<String> response = taskController.importTasks("tasks.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).importTasks("tasks.json");
    }
}
