package co.com.olozano.taskmanager.User.Controller;

import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void findById() throws Exception {
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        UserDto user = UserDto.builder()
                .id(userId)
                .name("User Test")
                .email("user@test.com")
                .password("testPassword")
                .build();

        when(userService.findById(user.id()))
                .thenReturn(user);


        mockMvc.perform(get("/api/v1/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(userId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("User Test"))
                .andExpect(jsonPath("$.email")
                        .value("user@test.com"))
                .andExpect(jsonPath("$.password")
                        .value("testPassword"));
    }

    @Test
    void register() throws Exception {
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        UserDto user = UserDto.builder()
                .name("User Test")
                .email("user@test.com")
                .password("testPassword")
                .build();

        String userJson = "{"
                + "\"name\": \"User Test\","
                + "\"email\": \"user@test.com\","
                + "\"password\": \"testPassword\""
                + "}";

        UserDto userResponse = user.toBuilder()
                .id(userId)
                .build();

        when(userService.save(user))
                .thenReturn(userResponse);

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(userId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("User Test"))
                .andExpect(jsonPath("$.email")
                        .value("user@test.com"))
                .andExpect(jsonPath("$.password")
                        .value("testPassword"));
    }

    @Test
    void update() throws Exception {
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        UserDto user = UserDto.builder()
                .name("User Modification")
                .email("user@modificado.com")
                .password("testPasswordModification")
                .build();

        String userJson = "{"
                + "\"name\": \"User Modification\","
                + "\"email\": \"user@modificado.com\","
                + "\"password\": \"testPasswordModification\""
                + "}";

        UserDto userResponse = user.toBuilder()
                .id(userId)
                .build();

        when(userService.update(userId, user))
                .thenReturn(userResponse);

        mockMvc.perform(put("/api/v1/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(userId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("User Modification"))
                .andExpect(jsonPath("$.email")
                        .value("user@modificado.com"))
                .andExpect(jsonPath("$.password")
                        .value("testPasswordModification"));
    }
}