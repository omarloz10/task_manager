package co.com.olozano.taskmanager.Auth.Service;

import co.com.olozano.taskmanager.Auth.DTO.AuthDto;
import co.com.olozano.taskmanager.Auth.DTO.AuthResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService {
    AuthResponseDTO login(AuthDto authDto);

    AuthResponseDTO refreshToken(String refreshToken);

    UUID getCurrentUserId();
}
