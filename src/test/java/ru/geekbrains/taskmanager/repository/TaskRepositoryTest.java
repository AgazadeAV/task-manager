package ru.geekbrains.taskmanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.geekbrains.taskmanager.entity.Task;
import ru.geekbrains.taskmanager.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindByStatus() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        taskRepository.save(task);

        List<Task> tasks = taskRepository.findByStatus(TaskStatus.TODO);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
    }

    @Test
    public void testFindByDueDate() {
        LocalDate dueDate = LocalDate.now();
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDueDate(dueDate);
        taskRepository.save(task);

        List<Task> tasks = taskRepository.findByDueDate(dueDate);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(dueDate, tasks.get(0).getDueDate());
    }

    @Test
    public void testSearchByKeyword() {
        Task task = new Task();
        task.setTitle("Keyword Test Task");
        task.setDescription("This is a description with a keyword.");
        taskRepository.save(task);

        List<Task> tasks = taskRepository.searchByKeyword("keyword");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getTitle().contains("Keyword") || tasks.get(0).getDescription().contains("keyword"));
    }
}
