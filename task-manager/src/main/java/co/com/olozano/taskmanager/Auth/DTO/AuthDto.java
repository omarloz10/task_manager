package co.com.olozano.taskmanager.Auth.DTO;

import lombok.Builder;

@Builder(toBuilder = true)
public record AuthDto(
        String email,
        String password
) {
}
