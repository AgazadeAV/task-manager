package ru.geekbrains.taskmanager.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.geekbrains.taskmanager.dto.ProjectDTO;
import ru.geekbrains.taskmanager.entity.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProjectMapperTest {

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    @Test
    public void testMapToEntity() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");
        projectDTO.setDescription("Test Description");

        Project project = projectMapper.mapToEntity(projectDTO);

        assertNotNull(project);
        assertEquals("Test Project", project.getName());
        assertEquals("Test Description", project.getDescription());
        assertTrue(project.getTasks().isEmpty());
        assertNull(project.getUser());
    }


    @Test
    public void testMapToDto() {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");

        ProjectDTO projectDTO = projectMapper.mapToDto(project);

        assertNotNull(projectDTO);
        assertEquals("Test Project", projectDTO.getName());
        assertEquals("Test Description", projectDTO.getDescription());
        assertNull(projectDTO.getTasks());
    }

    @Test
    public void testUpdateEntity() {
        Project project = new Project();
        project.setName("Old Project");
        project.setDescription("Old Description");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        projectMapper.updateEntity(projectDTO, project);

        assertNotNull(project);
        assertEquals("Updated Project", project.getName());
        assertEquals("Updated Description", project.getDescription());
    }
}
