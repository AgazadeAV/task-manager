package ru.geekbrains.taskmanager.swagger.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.ProjectDTO;
import ru.geekbrains.taskmanager.entity.Project;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@ParameterObject
@Tag(name = "Project API")
public interface ProjectApiSpec {

    @Operation(
            summary = "Create a new project",
            description = "Creates a new project and returns its details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<ProjectDTO> createProject(ProjectDTO projectDTO);

    @Operation(
            summary = "Get all projects",
            description = "Fetches the list of all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<ProjectDTO> getAllProjects();

    @Operation(
            summary = "Get a project by ID",
            description = "Fetches the details of a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<ProjectDTO> getProjectById(Long id);

    @Operation(
            summary = "Update a project by ID",
            description = "Updates the details of an existing project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<ProjectDTO> updateProject(Long id, ProjectDTO projectDTO);

    @Operation(
            summary = "Delete a project by ID",
            description = "Deletes a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Void> deleteProject(Long id);

    @Operation(
            summary = "Get task metrics for a project",
            description = "Fetches the task metrics for a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task metrics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    ResponseEntity<Map<TaskStatus, Long>> getTaskMetrics(Long id);

    @Operation(
            summary = "Export projects to JSON",
            description = "Exports the list of projects to a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects exported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to export projects",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> exportProjects(String filePath) throws IOException;

    @Operation(
            summary = "Import projects from JSON",
            description = "Imports the list of projects from a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects imported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to import projects",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> importProjects(String filePath) throws IOException;
}
