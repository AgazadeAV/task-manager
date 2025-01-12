package ru.geekbrains.taskmanager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.User;
import ru.geekbrains.taskmanager.repository.ProjectRepository;
import ru.geekbrains.taskmanager.repository.TaskRepository;
import ru.geekbrains.taskmanager.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataExportImportService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public DataExportImportService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public void exportTasks(String filePath) throws IOException {
        List<Task> tasks = taskRepository.findAll();
        objectMapper.writeValue(new File(filePath), tasks);
    }

    public void exportProjects(String filePath) throws IOException {
        List<Project> projects = projectRepository.findAll();
        objectMapper.writeValue(new File(filePath), projects);
    }

    public void importTasks(String filePath) throws IOException {
        List<Task> tasks = objectMapper.readValue(new File(filePath), new TypeReference<List<Task>>() {});
        taskRepository.saveAll(tasks);
    }

    public void importProjects(String filePath) throws IOException {
        List<Project> projects = objectMapper.readValue(new File(filePath), new TypeReference<List<Project>>() {});
        projectRepository.saveAll(projects);
    }

    public void exportUsers(String filePath) throws IOException {
        List<User> users = userRepository.findAll();
        objectMapper.writeValue(new File(filePath), users);
    }

    public void importUsers(String filePath) throws IOException {
        List<User> users = objectMapper.readValue(new File(filePath), new TypeReference<List<User>>() {});
        userRepository.saveAll(users);
    }
}
