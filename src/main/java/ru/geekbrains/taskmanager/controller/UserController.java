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
import ru.geekbrains.taskmanager.dto.UserDTO;
import ru.geekbrains.taskmanager.entity.User;
import ru.geekbrains.taskmanager.mapper.UserMapper;
import ru.geekbrains.taskmanager.service.DataExportImportService;
import ru.geekbrains.taskmanager.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UserController.API_USER)
@RequiredArgsConstructor
public class UserController {

    public static final String API_USER = "/api/users";
    public static final String USER_ID = "/{id}";
    public static final String EXPORT_USER_TO_JSON = "/export";
    public static final String IMPORT_TASK_FROM_JSON = "/import";

    private final UserService userService;
    private final DataExportImportService dataExportImportService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @GetMapping(USER_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(UserMapper.toDTO(createdUser));
    }

    @PutMapping(USER_ID)
    public ResponseEntity<UserDTO> updateUser(@PathVariable (name = "id") Long id, @RequestBody UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
    }

    @DeleteMapping(USER_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable (name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(EXPORT_USER_TO_JSON)
    public ResponseEntity<String> exportUsers(@RequestParam String filePath) {
        try {
            dataExportImportService.exportUsers(filePath);
            return ResponseEntity.ok("Users exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export users: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_TASK_FROM_JSON)
    public ResponseEntity<String> importUsers(@RequestParam String filePath) {
        try {
            dataExportImportService.importUsers(filePath);
            return ResponseEntity.ok("Users imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import users: " + e.getMessage());
        }
    }
}
