package ru.geekbrains.taskmanager.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.exception.ProjectNotFoundException;
import ru.geekbrains.taskmanager.repository.ProjectRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    public ProjectServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProjects() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Test Description");

        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testGetProjectById_Success() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProjectById_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(1L));
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setName("New Project");
        project.setDescription("New Description");
        when(projectRepository.save(project)).thenReturn(project);

        Project createdProject = projectService.createProject(project);

        assertNotNull(createdProject);
        assertEquals("New Project", createdProject.getName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testUpdateProject() {
        Project existingProject = new Project();
        existingProject.setId(1L);
        existingProject.setName("Old Project");

        Project updatedProject = new Project();
        updatedProject.setName("Updated Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(existingProject);

        Project result = projectService.updateProject(1L, updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getName());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    public void testDeleteProject() {
        when(projectRepository.existsById(1L)).thenReturn(true);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProject_NotFound() {
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(1L));
        verify(projectRepository, times(1)).existsById(1L);
    }

    @Test
    public void testGetTaskMetrics() {
        Project project = new Project();
        project.setId(1L);
        project.setTasks(List.of(
                new Task(1L, "Task 1", "Description 1", TaskStatus.TODO, null, null, null),
                new Task(2L, "Task 2", "Description 2", TaskStatus.DONE, null, null, null),
                new Task(3L, "Task 3", "Description 3", TaskStatus.DONE, null, null, null)
        ));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Map<TaskStatus, Long> metrics = projectService.getTaskMetrics(1L);

        assertNotNull(metrics);
        assertEquals(1, metrics.get(TaskStatus.TODO));
        assertEquals(2, metrics.get(TaskStatus.DONE));
        verify(projectRepository, times(1)).findById(1L);
    }
}
