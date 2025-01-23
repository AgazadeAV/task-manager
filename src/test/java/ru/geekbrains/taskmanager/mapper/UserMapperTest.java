package ru.geekbrains.taskmanager.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.geekbrains.taskmanager.dto.UserDTO;
import ru.geekbrains.taskmanager.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testMapToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUser");

        User user = userMapper.mapToEntity(userDTO);

        assertNotNull(user);
        assertEquals("TestUser", user.getUsername());
        assertTrue(user.getProjects().isEmpty());
    }


    @Test
    public void testMapToDto() {
        User user = new User();
        user.setUsername("TestUser");

        UserDTO userDTO = userMapper.mapToDto(user);

        assertNotNull(userDTO);
        assertEquals("TestUser", userDTO.getUsername());
        assertNull(userDTO.getProjects());
    }

    @Test
    public void testUpdateEntity() {
        User user = new User();
        user.setUsername("OldUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("UpdatedUser");

        userMapper.updateEntity(userDTO, user);

        assertNotNull(user);
        assertEquals("UpdatedUser", user.getUsername());
    }
}
