package ru.geekbrains.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.taskmanager.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
