package ru.geekbrains.taskmanager.swagger.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.geekbrains.taskmanager.dto.ProjectDTO;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskDtoSchema {

    @Schema(
            example = "Some task title",
            description = "Title of the task."
    )
    private String title;

    @Schema(
            example = "Some task description",
            description = "Description of the task."
    )
    private String description;

    @Schema(
            example = "Some task status, should be TODO, IN_PROGRESS or DONE",
            description = "Status of the task."
    )
    private String status;

    @Schema(
            example = "Some task due date should be in the future: 2035-01-23",
            description = "Due date of the task.",
            type = "string",
            format = "date"
    )
    private LocalDate dueDate;

    @Schema(
            description = "Project associated with the task."
    )
    private ProjectDTO project;
}
