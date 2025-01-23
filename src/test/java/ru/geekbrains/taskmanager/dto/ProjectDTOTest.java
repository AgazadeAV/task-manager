package ru.geekbrains.taskmanager.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProjectDTOTest {

    @Test
    public void testBuilder() {
        String name = "Test Project";
        String description = "Test Description";

        ProjectDTO projectDTO = ProjectDTO.builder()
                .name(name)
                .description(description)
                .tasks(Collections.emptyList())
                .user(new UserDTO())
                .build();

        assertNotNull(projectDTO);
        assertEquals(name, projectDTO.getName());
        assertEquals(description, projectDTO.getDescription());
        assertNotNull(projectDTO.getTasks());
        assertTrue(projectDTO.getTasks().isEmpty());
        assertNotNull(projectDTO.getUser());
    }

    @Test
    public void testNoArgsConstructor() {
        ProjectDTO projectDTO = new ProjectDTO();

        assertNotNull(projectDTO);
        assertNull(projectDTO.getName());
        assertNull(projectDTO.getDescription());
        assertNull(projectDTO.getTasks());
        assertNull(projectDTO.getUser());
    }

    @Test
    public void testAllArgsConstructor() {
        String name = "Test Project";
        String description = "Test Description";
        UserDTO user = new UserDTO();

        ProjectDTO projectDTO = new ProjectDTO(name, description, Collections.emptyList(), user);

        assertNotNull(projectDTO);
        assertEquals(name, projectDTO.getName());
        assertEquals(description, projectDTO.getDescription());
        assertNotNull(projectDTO.getTasks());
        assertTrue(projectDTO.getTasks().isEmpty());
        assertNotNull(projectDTO.getUser());
    }

    @Test
    public void testSettersAndGetters() {
        ProjectDTO projectDTO = new ProjectDTO();
        String name = "Test Project";
        String description = "Test Description";

        projectDTO.setName(name);
        projectDTO.setDescription(description);

        assertEquals(name, projectDTO.getName());
        assertEquals(description, projectDTO.getDescription());
    }
}
