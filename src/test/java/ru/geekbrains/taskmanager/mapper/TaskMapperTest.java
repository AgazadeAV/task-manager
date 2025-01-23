package ru.geekbrains.taskmanager.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testMapToEntity() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus("TODO");
        taskDTO.setDueDate(LocalDate.now());

        Task task = taskMapper.mapToEntity(taskDTO);

        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(LocalDate.now(), task.getDueDate());
        assertNull(task.getProject());
    }

    @Test
    public void testMapToDto() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setDueDate(LocalDate.now());

        TaskDTO taskDTO = taskMapper.mapToDto(task);

        assertNotNull(taskDTO);
        assertEquals("Test Task", taskDTO.getTitle());
        assertEquals("Test Description", taskDTO.getDescription());
        assertEquals("IN_PROGRESS", taskDTO.getStatus());
        assertEquals(LocalDate.now(), taskDTO.getDueDate());
    }

    @Test
    public void testUpdateEntity() {
        Task task = new Task();
        task.setTitle("Old Task");
        task.setDescription("Old Description");
        task.setStatus(TaskStatus.DONE);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");
        taskDTO.setDescription("Updated Description");
        taskDTO.setStatus("TODO");

        taskMapper.updateEntity(taskDTO, task);

        assertNotNull(task);
        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
    }
}
