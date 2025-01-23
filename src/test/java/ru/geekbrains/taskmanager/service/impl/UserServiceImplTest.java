package ru.geekbrains.taskmanager.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.geekbrains.taskmanager.entity.User;
import ru.geekbrains.taskmanager.exception.UserNotFoundException;
import ru.geekbrains.taskmanager.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("TestUser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("NewUser");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("NewUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("OldUser");

        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("UpdatedUser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
    }
}
