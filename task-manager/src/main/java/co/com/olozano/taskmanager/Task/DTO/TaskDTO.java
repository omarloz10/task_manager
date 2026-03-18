package co.com.olozano.taskmanager.Task.DTO;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record TaskDTO(
        UUID id,
        String title,
        String description,
        Boolean isCompleted
) {
}
