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
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.ProjectServiceImpl;
import ru.geekbrains.taskmanager.mapper.ProjectMapper;
import ru.geekbrains.taskmanager.swagger.specs.ProjectApiSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ProjectController.API_PROJECT)
@RequiredArgsConstructor
public class ProjectController implements ProjectApiSpec {

    public static final String API_PROJECT = "/api/projects";
    public static final String PROJECT_ID = "/{id}";
    public static final String PROJECT_METRICS = "/{id}/metrics";
    public static final String EXPORT_PROJECT_TO_JSON = "/export";
    public static final String IMPORT_PROJECT_FROM_JSON = "/import";

    private final ProjectServiceImpl projectServiceImpl;
    private final DataExportImportServiceImpl dataExportImportServiceImpl;
    private final ProjectMapper projectMapper;

    @GetMapping
    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectServiceImpl.getAllProjects().stream()
                .map(projectMapper::mapToDto)
                .toList();
    }

    @GetMapping(PROJECT_ID)
    @Override
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(projectMapper.mapToDto(projectServiceImpl.getProjectById(id)));
    }

    @PostMapping
    @Override
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectMapper.mapToEntity(projectDTO);
        Project createdProject = projectServiceImpl.createProject(project);
        return ResponseEntity.ok(projectMapper.mapToDto(createdProject));
    }

    @PutMapping(PROJECT_ID)
    @Override
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable(name = "id") Long id, @RequestBody ProjectDTO projectDTO) {
        Project project = projectMapper.mapToEntity(projectDTO);
        Project updatedProject = projectServiceImpl.updateProject(id, project);
        return ResponseEntity.ok(projectMapper.mapToDto(updatedProject));
    }

    @DeleteMapping(PROJECT_ID)
    @Override
    public ResponseEntity<Void> deleteProject(@PathVariable(name = "id") Long id) {
        projectServiceImpl.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(PROJECT_METRICS)
    @Override
    public ResponseEntity<Map<TaskStatus, Long>> getTaskMetrics(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(projectServiceImpl.getTaskMetrics(id));
    }

    @GetMapping(EXPORT_PROJECT_TO_JSON)
    @Override
    public ResponseEntity<String> exportProjects(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.exportProjects(filePath);
            return ResponseEntity.ok("Projects exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export projects: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_PROJECT_FROM_JSON)
    @Override
    public ResponseEntity<String> importProjects(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.importProjects(filePath);
            return ResponseEntity.ok("Projects imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import projects: " + e.getMessage());
        }
    }
}
