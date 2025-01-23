package ru.geekbrains.taskmanager.swagger.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.geekbrains.taskmanager.dto.ProjectDTO;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoSchema {

    @Schema(
            example = "Some username",
            description = "Username of the user."
    )
    private String username;

    @Schema(
            description = "List of projects associated with the user."
    )
    private List<ProjectDTO> projects;
}
