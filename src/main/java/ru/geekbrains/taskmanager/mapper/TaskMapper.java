package ru.geekbrains.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "project", ignore = true)
    Task mapToEntity(TaskDTO taskDTO);

    @Mapping(target = "project", ignore = true)
    TaskDTO mapToDto(Task task);

    void updateEntity(TaskDTO taskDTO, @MappingTarget Task task);
}
