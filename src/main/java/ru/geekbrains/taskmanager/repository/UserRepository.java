package ru.geekbrains.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.taskmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
