package ru.geekbrains.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.geekbrains.taskmanager.dto.UserDTO;
import ru.geekbrains.taskmanager.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "projects", ignore = true)
    User mapToEntity(UserDTO userDTO);

    @Mapping(target = "projects", ignore = true)
    UserDTO mapToDto(User user);

    void updateEntity(UserDTO userDTO, @MappingTarget User user);
}
