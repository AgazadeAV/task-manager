package ru.geekbrains.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.exception.ProjectNotFoundException;
import ru.geekbrains.taskmanager.repository.ProjectRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + id));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);
        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Cannot delete. Project not found with id " + id);
        }
        projectRepository.deleteById(id);
    }

    public Map<TaskStatus, Long> getTaskMetrics(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getTasks().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }

}
