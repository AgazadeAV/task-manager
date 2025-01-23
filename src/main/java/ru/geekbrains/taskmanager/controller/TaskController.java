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
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.TaskServiceImpl;
import ru.geekbrains.taskmanager.mapper.TaskMapper;
import ru.geekbrains.taskmanager.swagger.specs.TaskApiSpec;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(TaskController.API_TASK)
@RequiredArgsConstructor
public class TaskController implements TaskApiSpec {

    public static final String API_TASK = "/api/tasks";
    public static final String TASK_ID = "/{id}";
    public static final String FILTER_TASK_BY_STATUS = "/filter/status";
    public static final String FILTER_TASK_BY_DATE = "/filter/date";
    public static final String SEARCH_TASK = "/search";
    public static final String EXPORT_TASK_TO_JSON = "/export";
    public static final String IMPORT_TASK_FROM_JSON = "/import";

    private final TaskServiceImpl taskServiceImpl;
    private final DataExportImportServiceImpl dataExportImportServiceImpl;
    private final TaskMapper taskMapper;

    @GetMapping
    @Override
    public List<TaskDTO> getAllTasks() {
        return taskServiceImpl.getAllTasks().stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    @GetMapping(TASK_ID)
    @Override
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable(name = "id") Long id) {
        Task task = taskServiceImpl.getTaskById(id);
        return ResponseEntity.ok(taskMapper.mapToDto(task));
    }

    @PostMapping
    @Override
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapToEntity(taskDTO);
        Task createdTask = taskServiceImpl.createTask(task);
        return ResponseEntity.ok(taskMapper.mapToDto(createdTask));
    }

    @PutMapping(TASK_ID)
    @Override
    public ResponseEntity<TaskDTO> updateTask(@PathVariable(name = "id") Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapToEntity(taskDTO);
        Task updatedTask = taskServiceImpl.updateTask(id, task);
        return ResponseEntity.ok(taskMapper.mapToDto(updatedTask));
    }

    @DeleteMapping(TASK_ID)
    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
        taskServiceImpl.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(FILTER_TASK_BY_STATUS)
    @Override
    public List<TaskDTO> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskServiceImpl.getTasksByStatus(status).stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    @GetMapping(FILTER_TASK_BY_DATE)
    @Override
    public List<TaskDTO> getTasksByDueDate(@RequestParam LocalDate dueDate) {
        return taskServiceImpl.getTasksByDueDate(dueDate).stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    @GetMapping(SEARCH_TASK)
    @Override
    public List<TaskDTO> searchTasks(@RequestParam String keyword) {
        return taskServiceImpl.searchTasks(keyword).stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    @GetMapping(EXPORT_TASK_TO_JSON)
    @Override
    public ResponseEntity<String> exportTasks(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.exportTasks(filePath);
            return ResponseEntity.ok("Tasks exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export tasks: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_TASK_FROM_JSON)
    @Override
    public ResponseEntity<String> importTasks(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.importTasks(filePath);
            return ResponseEntity.ok("Tasks imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import tasks: " + e.getMessage());
        }
    }
}
