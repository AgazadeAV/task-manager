package ru.geekbrains.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.mapper.TaskMapper;
import ru.geekbrains.taskmanager.service.DataExportImportService;
import ru.geekbrains.taskmanager.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(TaskController.API_TASK)
public class TaskController {

    public static final String API_TASK = "/api/tasks";
    public static final String TASK_ID = "/{id}";
    public static final String FILTER_TASK_BY_STATUS = "/filter/status";
    public static final String FILTER_TASK_BY_DATE = "/filter/date";
    public static final String SEARCH_TASK = "/search";
    public static final String EXPORT_TASK_TO_JSON = "/export";
    public static final String IMPORT_TASK_FROM_JSON = "/import";

    private final TaskService taskService;
    private final DataExportImportService dataExportImportService;

    public TaskController(TaskService taskService, DataExportImportService dataExportImportService) {
        this.taskService = taskService;
        this.dataExportImportService = dataExportImportService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    @GetMapping(TASK_ID)
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable (name = "id") Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(TaskMapper.toDTO(task));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(TaskMapper.toDTO(createdTask));
    }

    @PutMapping(TASK_ID)
    public ResponseEntity<TaskDTO> updateTask(@PathVariable (name = "id") Long id, @RequestBody TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(TaskMapper.toDTO(updatedTask));
    }

    @DeleteMapping(TASK_ID)
    public ResponseEntity<Void> deleteTask(@PathVariable (name = "id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(FILTER_TASK_BY_STATUS)
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping(FILTER_TASK_BY_DATE)
    public List<Task> getTasksByDueDate(@RequestParam LocalDate dueDate) {
        return taskService.getTasksByDueDate(dueDate);
    }

    @GetMapping(SEARCH_TASK)
    public List<Task> searchTasks(@RequestParam String keyword) {
        return taskService.searchTasks(keyword);
    }

    @GetMapping(EXPORT_TASK_TO_JSON)
    public ResponseEntity<String> exportTasks(@RequestParam String filePath) {
        try {
            dataExportImportService.exportTasks(filePath);
            return ResponseEntity.ok("Tasks exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export tasks: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_TASK_FROM_JSON)
    public ResponseEntity<String> importTasks(@RequestParam String filePath) {
        try {
            dataExportImportService.importTasks(filePath);
            return ResponseEntity.ok("Tasks imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import tasks: " + e.getMessage());
        }
    }
}
