package com.gabriel.moraes.springtodoapp.domain.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "TaskDto Model Information"
)
public class TaskDto {
    private long id;

    @Schema(
            description = "Task description"
    )
    @NotEmpty
    @Size(min = 2, max = 260, message = "Task description must have 2 characters at least.")
    private String description;

    @Schema(
            description = "Task completed status"
    )
    private boolean completed;
}
