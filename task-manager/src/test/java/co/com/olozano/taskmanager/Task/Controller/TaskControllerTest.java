package co.com.olozano.taskmanager.Task.Controller;

import co.com.olozano.taskmanager.Task.DTO.TaskDTO;
import co.com.olozano.taskmanager.Task.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }


    @Test
    void getTaskById() throws Exception {
        UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        TaskDTO taskDTO = TaskDTO.builder()
                .id(taskId)
                .title("Task Title 1")
                .description("Task Description 1")
                .isCompleted(false)
                .build();

        when(taskService.findById(taskId))
                .thenReturn(taskDTO);

        mockMvc.perform(get("/api/v1/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId.toString()))
                .andExpect(jsonPath("$.title")
                        .value("Task Title 1"))
                .andExpect(jsonPath("$.description")
                        .value("Task Description 1"))
                .andExpect(jsonPath("$.isCompleted")
                        .value(false));

    }

    @Test
    void getAllTasks() throws Exception {
        UUID taskIdOne = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID taskIdTwo = UUID.fromString("ee3512af-ec68-43cf-8aba-239907526cf2");

        TaskDTO taskOne = TaskDTO.builder()
                .id(taskIdOne)
                .title("Task Title 1")
                .description("Task Description 1")
                .isCompleted(false)
                .build();

        TaskDTO taskTwo = TaskDTO.builder()
                .id(taskIdTwo)
                .title("Task Title 2")
                .description("Task Description 2")
                .isCompleted(true)
                .build();

        List<TaskDTO> taskDTOList = List.of(taskOne, taskTwo);

        when(taskService.findAll())
                .thenReturn(taskDTOList);

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id")
                        .value(taskIdOne.toString()))
                .andExpect(jsonPath("$[0].title")
                        .value("Task Title 1"))
                .andExpect(jsonPath("$[0].description")
                        .value("Task Description 1"))
                .andExpect(jsonPath("$[0].isCompleted")
                        .value(false))
                .andExpect(jsonPath("$[1].id")
                        .value(taskIdTwo.toString()))
                .andExpect(jsonPath("$[1].title")
                        .value("Task Title 2"))
                .andExpect(jsonPath("$[1].description")
                        .value("Task Description 2"))
                .andExpect(jsonPath("$[1].isCompleted")
                        .value(true));
    }

    @Test
    void createTask() throws Exception {
        UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        TaskDTO taskDTO = TaskDTO.builder()
                .title("Task Title 1")
                .description("Task Description 1")
                .build();

        TaskDTO taskResponse = TaskDTO.builder()
                .id(taskId)
                .title("Task Title 1")
                .description("Task Description 1")
                .isCompleted(false)
                .build();

        String taskJson = "{" +
                "\"title\": \"Task Title 1\"," +
                "\"description\": \"Task Description 1\"" +
                "}";

        when(taskService.save(taskDTO))
                .thenReturn(taskResponse);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(taskId.toString()))
                .andExpect(jsonPath("$.title")
                        .value("Task Title 1"))
                .andExpect(jsonPath("$.description")
                        .value("Task Description 1"))
                .andExpect(jsonPath("$.isCompleted")
                        .value(false));
    }

    @Test
    void updateTask() throws Exception {
        UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        TaskDTO taskDTO = TaskDTO.builder()
                .title("Task Modification")
                .description("Task Modification")
                .build();

        TaskDTO taskResponse = TaskDTO.builder()
                .id(taskId)
                .title("Task Modification")
                .description("Task Modification")
                .isCompleted(false)
                .build();

        String taskJson = "{" +
                "\"title\": \"Task Modification\"," +
                "\"description\": \"Task Modification\"" +
                "}";

        when(taskService.update(taskId, taskDTO))
                .thenReturn(taskResponse);

        mockMvc.perform(put("/api/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId.toString()))
                .andExpect(jsonPath("$.title")
                        .value("Task Modification"))
                .andExpect(jsonPath("$.description")
                        .value("Task Modification"))
                .andExpect(jsonPath("$.isCompleted")
                        .value(false));
    }

    @Test
    void deleteTask() throws Exception {
        UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/v1/tasks/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    void changeStatus() throws Exception {
        UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        TaskDTO taskResponse = TaskDTO.builder()
                .id(taskId)
                .title("Task Title 1")
                .description("Task Description 1")
                .isCompleted(true)
                .build();

        when(taskService.changeStatus(taskId))
                .thenReturn(taskResponse);

        mockMvc.perform(patch("/api/v1/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(taskId.toString()))
                .andExpect(jsonPath("$.title")
                        .value("Task Title 1"))
                .andExpect(jsonPath("$.description")
                        .value("Task Description 1"))
                .andExpect(jsonPath("$.isCompleted")
                        .value(true));
    }
}