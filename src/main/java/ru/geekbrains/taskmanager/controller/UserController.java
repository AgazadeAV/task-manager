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
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.UserServiceImpl;
import ru.geekbrains.taskmanager.mapper.UserMapper;
import ru.geekbrains.taskmanager.swagger.specs.UserApiSpec;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UserController.API_USER)
@RequiredArgsConstructor
public class UserController implements UserApiSpec {

    public static final String API_USER = "/api/users";
    public static final String USER_ID = "/{id}";
    public static final String EXPORT_USER_TO_JSON = "/export";
    public static final String IMPORT_TASK_FROM_JSON = "/import";

    private final UserServiceImpl userServiceImpl;
    private final DataExportImportServiceImpl dataExportImportServiceImpl;
    private final UserMapper userMapper;

    @GetMapping
    @Override
    public List<UserDTO> getAllUsers() {
        return userServiceImpl.getAllUsers().stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @GetMapping(USER_ID)
    @Override
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userMapper.mapToDto(userServiceImpl.getUserById(id)));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        User createdUser = userServiceImpl.createUser(user);
        return ResponseEntity.ok(userMapper.mapToDto(createdUser));
    }

    @PutMapping(USER_ID)
    @Override
    public ResponseEntity<UserDTO> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        User updatedUser = userServiceImpl.updateUser(id, user);
        return ResponseEntity.ok(userMapper.mapToDto(updatedUser));
    }

    @DeleteMapping(USER_ID)
    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(EXPORT_USER_TO_JSON)
    @Override
    public ResponseEntity<String> exportUsers(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.exportUsers(filePath);
            return ResponseEntity.ok("Users exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to export users: " + e.getMessage());
        }
    }

    @PostMapping(IMPORT_TASK_FROM_JSON)
    @Override
    public ResponseEntity<String> importUsers(@RequestParam String filePath) {
        try {
            dataExportImportServiceImpl.importUsers(filePath);
            return ResponseEntity.ok("Users imported successfully from " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to import users: " + e.getMessage());
        }
    }
}
