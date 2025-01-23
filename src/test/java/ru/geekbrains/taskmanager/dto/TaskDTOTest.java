package ru.geekbrains.taskmanager.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskDTOTest {

    @Test
    public void testBuilder() {
        String title = "Test Task";
        String description = "Test Description";
        String status = "TODO";
        LocalDate dueDate = LocalDate.now().plusDays(1);

        TaskDTO taskDTO = TaskDTO.builder()
                .title(title)
                .description(description)
                .status(status)
                .dueDate(dueDate)
                .project(new ProjectDTO())
                .build();

        assertNotNull(taskDTO);
        assertEquals(title, taskDTO.getTitle());
        assertEquals(description, taskDTO.getDescription());
        assertEquals(status, taskDTO.getStatus());
        assertEquals(dueDate, taskDTO.getDueDate());
        assertNotNull(taskDTO.getProject());
    }

    @Test
    public void testNoArgsConstructor() {
        TaskDTO taskDTO = new TaskDTO();

        assertNotNull(taskDTO);
        assertNull(taskDTO.getTitle());
        assertNull(taskDTO.getDescription());
        assertNull(taskDTO.getStatus());
        assertNull(taskDTO.getDueDate());
        assertNull(taskDTO.getProject());
    }

    @Test
    public void testAllArgsConstructor() {
        String title = "Test Task";
        String description = "Test Description";
        String status = "TODO";
        LocalDate dueDate = LocalDate.now().plusDays(1);
        ProjectDTO project = new ProjectDTO();

        TaskDTO taskDTO = new TaskDTO(title, description, status, dueDate, project);

        assertNotNull(taskDTO);
        assertEquals(title, taskDTO.getTitle());
        assertEquals(description, taskDTO.getDescription());
        assertEquals(status, taskDTO.getStatus());
        assertEquals(dueDate, taskDTO.getDueDate());
        assertNotNull(taskDTO.getProject());
    }

    @Test
    public void testSettersAndGetters() {
        TaskDTO taskDTO = new TaskDTO();
        String title = "Test Task";
        String description = "Test Description";
        String status = "TODO";
        LocalDate dueDate = LocalDate.now().plusDays(1);

        taskDTO.setTitle(title);
        taskDTO.setDescription(description);
        taskDTO.setStatus(status);
        taskDTO.setDueDate(dueDate);

        assertEquals(title, taskDTO.getTitle());
        assertEquals(description, taskDTO.getDescription());
        assertEquals(status, taskDTO.getStatus());
        assertEquals(dueDate, taskDTO.getDueDate());
    }
}
