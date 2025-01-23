package ru.geekbrains.taskmanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.geekbrains.taskmanager.entity.Project;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testSaveProject() {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");

        Project savedProject = projectRepository.save(project);

        assertNotNull(savedProject.getId());
        assertEquals("Test Project", savedProject.getName());
        assertEquals("Test Description", savedProject.getDescription());
    }

    @Test
    public void testFindById() {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");
        Project savedProject = projectRepository.save(project);

        Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

        assertTrue(foundProject.isPresent());
        assertEquals("Test Project", foundProject.get().getName());
    }

    @Test
    public void testDeleteProject() {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");
        Project savedProject = projectRepository.save(project);

        projectRepository.deleteById(savedProject.getId());
        Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

        assertFalse(foundProject.isPresent());
    }
}
