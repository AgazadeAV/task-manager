package ru.geekbrains.taskmanager.swagger.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@ParameterObject
@Tag(name = "Task API")
public interface TaskApiSpec {

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task and returns its details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<TaskDTO> createTask(TaskDTO taskDTO);

    @Operation(
            summary = "Get all tasks",
            description = "Fetches the list of all tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<TaskDTO> getAllTasks();

    @Operation(
            summary = "Get a task by ID",
            description = "Fetches the details of a task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<TaskDTO> getTaskById(Long id);

    @Operation(
            summary = "Update a task by ID",
            description = "Updates the details of an existing task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<TaskDTO> updateTask(Long id, TaskDTO taskDTO);

    @Operation(
            summary = "Delete a task by ID",
            description = "Deletes a task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Void> deleteTask(Long id);

    @Operation(
            summary = "Get tasks by status",
            description = "Fetches the list of tasks filtered by status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<TaskDTO> getTasksByStatus(TaskStatus status);

    @Operation(
            summary = "Get tasks by due date",
            description = "Fetches the list of tasks filtered by due date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<TaskDTO> getTasksByDueDate(LocalDate dueDate);

    @Operation(
            summary = "Search tasks by keyword",
            description = "Fetches the list of tasks that match the keyword.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<TaskDTO> searchTasks(String keyword);

    @Operation(
            summary = "Export tasks to JSON",
            description = "Exports the list of tasks to a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks exported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to export tasks",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> exportTasks(String filePath) throws IOException;

    @Operation(
            summary = "Import tasks from JSON",
            description = "Imports the list of tasks from a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks imported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to import tasks",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> importTasks(String filePath) throws IOException;
}
