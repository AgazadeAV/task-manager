package ru.geekbrains.taskmanager.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleGlobalException() {
        Exception exception = new Exception("Test exception");

        ResponseEntity<String> response = handler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Test exception", response.getBody());
    }

    @Test
    public void testHandleTaskNotFoundException() {
        TaskNotFoundException exception = new TaskNotFoundException("Task not found");

        ResponseEntity<String> response = handler.handleTaskNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Task not found", response.getBody());
    }

    @Test
    public void testHandleProjectNotFoundException() {
        ProjectNotFoundException exception = new ProjectNotFoundException("Project not found");

        ResponseEntity<String> response = handler.handleProjectNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Project not found", response.getBody());
    }

    @Test
    public void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<String> response = handler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }
}
