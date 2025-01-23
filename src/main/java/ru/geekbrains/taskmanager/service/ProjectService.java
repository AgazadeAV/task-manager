package ru.geekbrains.taskmanager.service;

import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    Project createProject(Project project);

    Project updateProject(Long id, Project updatedProject);

    void deleteProject(Long id);

    Map<TaskStatus, Long> getTaskMetrics(Long projectId);
}
