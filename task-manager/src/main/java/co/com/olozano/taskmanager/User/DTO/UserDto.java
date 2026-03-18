package co.com.olozano.taskmanager.User.DTO;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserDto(
        UUID id,
        String name,
        String email,
        String password
) {
}
