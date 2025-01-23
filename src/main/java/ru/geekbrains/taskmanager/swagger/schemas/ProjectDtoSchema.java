package ru.geekbrains.taskmanager.swagger.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.geekbrains.taskmanager.dto.TaskDTO;
import ru.geekbrains.taskmanager.dto.UserDTO;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectDtoSchema {

    @Schema(
            example = "Some project",
            description = "Name of the project."
    )
    private String name;

    @Schema(
            example = "Some project description",
            description = "Description of the project."
    )
    private String description;

    @Schema(
            description = "List of tasks in the project."
    )
    private List<TaskDTO> tasks;

    @Schema(
            description = "User associated with the project."
    )
    private UserDTO user;
}
