package co.com.olozano.taskmanager.Auth.Service;

import co.com.olozano.taskmanager.Auth.DTO.AuthDto;
import co.com.olozano.taskmanager.Auth.DTO.AuthResponseDTO;
import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Entity.User;
import co.com.olozano.taskmanager.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder encoder;


    @Override
    public AuthResponseDTO login(AuthDto authDto) {

        User user = userService.findByEmail(authDto.email());

        if (user == null) {
            throw new RuntimeException("This email is not registered");
        }

        if (!passwordEncoder.matches(authDto.password(), user.getPassword())) {
            throw new RuntimeException("Incorrect Password");
        }

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        var claimsAccess = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .claim("id", user.getId().toString())
                .claim("name", user.getName())
                .expiresAt(Instant.now().plusSeconds(900))
                .build();

        var claimsRefresh = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .claim("id", user.getId().toString())
                .claim("name", user.getName())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        System.out.println(encoder.encode(JwtEncoderParameters.from(jwsHeader, claimsAccess)));

        return AuthResponseDTO.builder()
                .access_token(encoder.encode(JwtEncoderParameters.from(jwsHeader, claimsAccess)).getTokenValue())
                .refresh_token(encoder.encode(JwtEncoderParameters.from(jwsHeader, claimsRefresh)).getTokenValue())
                .build();
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {

        Jwt decodedJwt;
        try {
            decodedJwt = jwtDecoder.decode(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = decodedJwt.getSubject();

        UserDto user = userService.findById(UUID.fromString(userId));

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        var claimsAccess = JwtClaimsSet.builder()
                .subject(user.id().toString())
                .issuedAt(Instant.now())
                .claim("id", user.id().toString())
                .claim("name", user.name())
                .expiresAt(Instant.now().plusSeconds(900)) // 15 min
                .build();

        return AuthResponseDTO.builder()
                .access_token(encoder.encode(JwtEncoderParameters.from(jwsHeader, claimsAccess)).getTokenValue())
                .refresh_token(refreshToken)
                .build();
    }

    @Override
    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            System.out.println(UUID.fromString(jwt.getClaimAsString("id")));
            return UUID.fromString(jwt.getClaimAsString("id"));
        }
        throw new RuntimeException("No authenticated user found");
    }
}
