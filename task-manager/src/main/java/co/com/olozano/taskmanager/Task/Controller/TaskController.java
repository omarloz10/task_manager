package co.com.olozano.taskmanager.Task.Controller;

import co.com.olozano.taskmanager.Task.DTO.TaskDTO;
import co.com.olozano.taskmanager.Task.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable(name = "taskId") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.findById(id));
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.findAll());
    }

    @PostMapping(path = {"", "/"})
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.save(taskDTO));
    }

    @PutMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable(name = "taskId") UUID id,
                                              @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.update(id, taskDTO));
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "taskId") UUID id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> changeStatus(@PathVariable(name = "taskId") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.changeStatus(id));
    }
}
