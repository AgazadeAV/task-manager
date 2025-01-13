package ru.geekbrains.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.taskmanager.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
