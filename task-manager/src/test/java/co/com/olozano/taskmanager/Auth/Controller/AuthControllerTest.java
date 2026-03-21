package co.com.olozano.taskmanager.Auth.Controller;


import co.com.olozano.taskmanager.Auth.DTO.AuthDto;
import co.com.olozano.taskmanager.Auth.DTO.AuthResponseDTO;
import co.com.olozano.taskmanager.Auth.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void login() throws Exception {

        AuthDto authDto = AuthDto.builder()
                .email("email@test.com")
                .password("123456789")
                .build();

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .access_token("access-token.jwt-para-testing")
                .refresh_token("refresh-token.jwt-para-testing")
                .build();

        when(authService.login(authDto))
                .thenReturn(authResponseDTO);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"email@test.com\",\"password\":\"123456789\"}")) // 👈 body JSON)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token.jwt-para-testing"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token.jwt-para-testing"));
    }

    @Test
    void refreshToken() throws Exception {

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .access_token("access-token.jwt-para-testing")
                .refresh_token("refresh-token.jwt-para-testing")
                .build();

        when(authService.refreshToken("refresh-token-request.jwt-para-testing"))
                .thenReturn(authResponseDTO);

        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("refresh-token-request.jwt-para-testing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token.jwt-para-testing"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token.jwt-para-testing"));
    }
}