package ru.geekbrains.taskmanager.dto;

import lombok.Data;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<Long> projectIds;
}
