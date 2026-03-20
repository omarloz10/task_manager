package co.com.olozano.taskmanager.Task.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record TaskDTO(
        UUID id,
        @NotBlank(message = "this field is required")
        String title,
        @NotBlank(message = "this field is required")
        String description,
        Boolean isCompleted
) {
}
