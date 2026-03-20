package co.com.olozano.taskmanager.Auth.Controller;

import co.com.olozano.taskmanager.Auth.DTO.AuthDto;
import co.com.olozano.taskmanager.Auth.DTO.AuthResponseDTO;
import co.com.olozano.taskmanager.Auth.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthDto authDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(authDto));
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.refreshToken(refreshToken));
    }

}
