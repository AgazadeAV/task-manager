package ru.geekbrains.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> taskIds;
    private Long userId;
}