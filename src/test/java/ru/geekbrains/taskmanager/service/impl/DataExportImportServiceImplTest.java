package ru.geekbrains.taskmanager.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.User;
import ru.geekbrains.taskmanager.repository.ProjectRepository;
import ru.geekbrains.taskmanager.repository.TaskRepository;
import ru.geekbrains.taskmanager.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataExportImportServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DataExportImportServiceImpl dataExportImportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExportTasks() throws IOException {
        List<Task> tasks = List.of(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        dataExportImportService.exportTasks("tasks.json");

        verify(objectMapper, times(1)).writeValue(any(File.class), eq(tasks));
    }

    @Test
    public void testExportProjects() throws IOException {
        List<Project> projects = List.of(new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        dataExportImportService.exportProjects("projects.json");

        verify(objectMapper, times(1)).writeValue(any(File.class), eq(projects));
    }

    @Test
    public void testImportTasks() throws IOException {
        List<Task> tasks = List.of(new Task());
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(tasks);

        dataExportImportService.importTasks("tasks.json");

        verify(taskRepository, times(1)).saveAll(eq(tasks));
    }

    @Test
    public void testImportProjects() throws IOException {
        List<Project> projects = List.of(new Project());
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(projects);

        dataExportImportService.importProjects("projects.json");

        verify(projectRepository, times(1)).saveAll(eq(projects));
    }

    @Test
    public void testExportUsers() throws IOException {
        List<User> users = List.of(new User());
        when(userRepository.findAll()).thenReturn(users);

        dataExportImportService.exportUsers("users.json");

        verify(objectMapper, times(1)).writeValue(any(File.class), eq(users));
    }

    @Test
    public void testImportUsers() throws IOException {
        List<User> users = List.of(new User());
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(users);

        dataExportImportService.importUsers("users.json");

        verify(userRepository, times(1)).saveAll(eq(users));
    }
}
