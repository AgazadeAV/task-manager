package ru.geekbrains.taskmanager.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDTOTest {

    @Test
    public void testBuilder() {
        String username = "TestUser";

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .projects(Collections.emptyList())
                .build();

        assertNotNull(userDTO);
        assertEquals(username, userDTO.getUsername());
        assertNotNull(userDTO.getProjects());
        assertTrue(userDTO.getProjects().isEmpty());
    }

    @Test
    public void testNoArgsConstructor() {
        UserDTO userDTO = new UserDTO();

        assertNotNull(userDTO);
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getProjects());
    }

    @Test
    public void testAllArgsConstructor() {
        String username = "TestUser";

        UserDTO userDTO = new UserDTO(username, Collections.emptyList());

        assertNotNull(userDTO);
        assertEquals(username, userDTO.getUsername());
        assertNotNull(userDTO.getProjects());
        assertTrue(userDTO.getProjects().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        UserDTO userDTO = new UserDTO();
        String username = "TestUser";

        userDTO.setUsername(username);

        assertEquals(username, userDTO.getUsername());
    }
}
