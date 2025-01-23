package ru.geekbrains.taskmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.UserDTO;
import ru.geekbrains.taskmanager.entity.User;
import ru.geekbrains.taskmanager.mapper.UserMapper;
import ru.geekbrains.taskmanager.service.impl.DataExportImportServiceImpl;
import ru.geekbrains.taskmanager.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private DataExportImportServiceImpl dataExportImportServiceImpl;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUser");

        when(userServiceImpl.getAllUsers()).thenReturn(List.of(user));
        when(userMapper.mapToDto(user)).thenReturn(userDTO);

        List<UserDTO> users = userController.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getUsername());
        verify(userServiceImpl, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUser");

        when(userServiceImpl.getUserById(1L)).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals("TestUser", response.getBody().getUsername());
        verify(userServiceImpl, times(1)).getUserById(1L);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("NewUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("NewUser");

        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(userServiceImpl.createUser(user)).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertNotNull(response);
        assertEquals("NewUser", response.getBody().getUsername());
        verify(userServiceImpl, times(1)).createUser(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("UpdatedUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("UpdatedUser");

        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(userServiceImpl.updateUser(1L, user)).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, userDTO);

        assertNotNull(response);
        assertEquals("UpdatedUser", response.getBody().getUsername());
        verify(userServiceImpl, times(1)).updateUser(1L, user);
    }

    @Test
    public void testDeleteUser() {
        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(userServiceImpl, times(1)).deleteUser(1L);
    }

    @Test
    public void testExportUsers() throws IOException {
        ResponseEntity<String> response = userController.exportUsers("users.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).exportUsers("users.json");
    }

    @Test
    public void testImportUsers() throws IOException {
        ResponseEntity<String> response = userController.importUsers("users.json");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(dataExportImportServiceImpl, times(1)).importUsers("users.json");
    }
}
