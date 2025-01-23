package ru.geekbrains.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.geekbrains.taskmanager.dto.ProjectDTO;
import ru.geekbrains.taskmanager.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "user", ignore = true)
    Project mapToEntity(ProjectDTO projectDTO);

    @Mapping(target = "tasks", ignore = true)
    ProjectDTO mapToDto(Project project);

    void updateEntity(ProjectDTO projectDTO, @MappingTarget Project project);
}
