package ru.geekbrains.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private List<Long> projectIds;
}
