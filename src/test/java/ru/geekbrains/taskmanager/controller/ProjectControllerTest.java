package ru.geekbrains.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.ProjectDTO;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.mapper.ProjectMapper;
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.ProjectServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    @Mock
    private ProjectServiceImpl projectServiceImpl;

    @Mock
    private DataExportImportServiceImpl dataExportImportServiceImpl;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProjects() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");

        when(projectServiceImpl.getAllProjects()).thenReturn(List.of(project));
        when(projectMapper.mapToDto(project)).thenReturn(projectDTO);

        List<ProjectDTO> projects = projectController.getAllProjects();

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getName());
        verify(projectServiceImpl, times(1)).getAllProjects();
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");

        when(projectServiceImpl.getProjectById(1L)).thenReturn(project);
        when(projectMapper.mapToDto(project)).thenReturn(projectDTO);

        ResponseEntity<ProjectDTO> response = projectController.getProjectById(1L);

        assertNotNull(response);
        assertEquals("Test Project", response.getBody().getName());
        verify(projectServiceImpl, times(1)).getProjectById(1L);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setName("New Project");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");

        when(projectMapper.mapToEntity(projectDTO)).thenReturn(project);
        when(projectServiceImpl.createProject(project)).thenReturn(project);
        when(projectMapper.mapToDto(project)).thenReturn(projectDTO);

        ResponseEntity<ProjectDTO> response = projectController.createProject(projectDTO);

        assertNotNull(response);
        assertEquals("New Project", response.getBody().getName());
        verify(projectServiceImpl, times(1)).createProject(project);
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();
        project.setName("Updated Project");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");

        when(projectMapper.mapToEntity(projectDTO)).thenReturn(project);
        when(projectServiceImpl.updateProject(1L, project)).thenReturn(project);
        when(projectMapper.mapToDto(project)).thenReturn(projectDTO);

        ResponseEntity<ProjectDTO> response = projectController.updateProject(1L, projectDTO);

        assertNotNull(response);
        assertEquals("Updated Project", response.getBody().getName());
        verify(projectServiceImpl, times(1)).updateProject(1L, project);
    }

    @Test
    public void testDeleteProject() {
        ResponseEntity<Void> response = projectController.deleteProject(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(projectServiceImpl, times(1)).deleteProject(1L);
    }

    @Test
    public void testGetTaskMetrics() {
        Map<TaskStatus, Long> metrics = Map.of(TaskStatus.TODO, 5L, TaskStatus.DONE, 3L);
        when(projectServiceImpl.getTaskMetrics(1L)).thenReturn(metrics);

        ResponseEntity<Map<TaskStatus, Long>> response = projectController.getTaskMetrics(1L);

        assertNotNull(response);
        assertEquals(5L, response.getBody().get(TaskStatus.TODO));
        verify(projectServiceImpl, times(1)).getTaskMetrics(1L);
    }

    @Test
    public void testExportProjects() throws IOException {
        ResponseEntity<String> response = projectController.exportProjects("projects.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).exportProjects("projects.json");
    }

    @Test
    public void testImportProjects() throws IOException {
        ResponseEntity<String> response = projectController.importProjects("projects.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).importProjects("projects.json");
    }
}
