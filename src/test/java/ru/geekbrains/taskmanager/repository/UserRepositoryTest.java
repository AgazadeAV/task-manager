package ru.geekbrains.taskmanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.geekbrains.taskmanager.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("TestUser");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("TestUser", savedUser.getUsername());
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("TestUser");
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("TestUser", foundUser.get().getUsername());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("TestUser");
        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertFalse(foundUser.isPresent());
    }
}
