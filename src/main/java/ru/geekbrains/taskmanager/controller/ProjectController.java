package ru.geekbrains.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.taskmanager.dto.ProjectDTO;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.mapper.ProjectMapper;
import ru.geekbrains.taskmanager.service.DataExportImportService;
import ru.geekbrains.taskmanager.service.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ProjectController.API_PROJECT)
@RequiredArgsConstructor
public class ProjectController {

    public static final String API_PROJECT = "/api/projects";
    public static final String PROJECT_ID = "/{id}";
    public static final String PROJECT_METRICS = "/{id}/metrics";
    public static final String EXPORT_PROJECT_TO_JSON = "/export";
    public static final String IMPORT_PROJECT_FROM_JSON = "/import";

    private final ProjectService projectService;
    private final DataExportImportService dataExportImportService;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects().stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    @GetMapping(PROJECT_ID)
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ProjectMapper.toDTO(projectService.getProjectById(id)));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = ProjectMapper.toEntity(projectDTO);
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(ProjectMapper.toDTO(createdProject));
    }

    @PutMapping(PROJECT_ID)
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable (name = "id") Long id, @RequestBody ProjectDTO projectDTO) {
        Project project = ProjectMapper.toEntity(projectDTO);
        Project updatedProject = projectService.updateProject(id, project);
        return ResponseEntity.ok(ProjectMapper.toDTO(updatedProject));
    }

    @DeleteMapping(PROJECT_ID)
    public ResponseEntity<Void> deleteProject(@PathVariable (name = "id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(PROJECT_METRICS)
    public ResponseEntity<Map<TaskStatus, Long>> getTaskMetrics(@PathVariable (name = "id") Long id) {
        return ResponseEntity.ok(projectService.getTaskMetrics(id));
    }

    @GetMapping(EXPORT_PROJECT_TO_JSON)
    public ResponseEntity<String> exportProjects(@RequestParam String filePath) {
        try {
            dataExportImportService.exportProjects(filePath);
            return ResponseEntity.ok("Projects exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export projects: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_PROJECT_FROM_JSON)
    public ResponseEntity<String> importProjects(@RequestParam String filePath) {
        try {
            dataExportImportService.importProjects(filePath);
            return ResponseEntity.ok("Projects imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import projects: " + e.getMessage());
        }
    }
}
