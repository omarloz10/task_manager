package co.com.olozano.taskmanager.User.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserDto(
        UUID id,
        @NotBlank(message = "this field is required")
        String name,
        @NotBlank(message = "this field is required")
        @Email(message = "the field email is invalid")
        String email,
        @NotBlank(message = "this field is required")
        @Length(min = 6, message = "this field must have at least 6 characters")
        String password
) {
}
