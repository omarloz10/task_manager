package co.com.olozano.taskmanager.Auth.DTO;

import lombok.Builder;

@Builder(toBuilder = true)
public record AuthResponseDTO(
        String access_token,
        String refresh_token
) {
}
