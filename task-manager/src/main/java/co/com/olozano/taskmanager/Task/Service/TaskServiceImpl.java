package co.com.olozano.taskmanager.Task.Service;

import co.com.olozano.taskmanager.Auth.Service.AuthService;
import co.com.olozano.taskmanager.Exception.Exceptions.NotFoundException;
import co.com.olozano.taskmanager.Task.DTO.TaskDTO;
import co.com.olozano.taskmanager.Task.Entity.Task;
import co.com.olozano.taskmanager.Task.Mapper.TaskMapper;
import co.com.olozano.taskmanager.Task.Repository.TaskRepository;
import co.com.olozano.taskmanager.User.Entity.User;
import co.com.olozano.taskmanager.User.Mapper.UserMapper;
import co.com.olozano.taskmanager.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDTO> findAll() {
        return taskMapper.toDTOs(taskRepository.findByUser_Id(authService.getCurrentUserId()));
    }

    @Override
    public TaskDTO findById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        return taskMapper.toDTO(task);
    }

    @Override
    public void deleteById(UUID id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {

        Task task = taskMapper.toEntity(taskDTO);
        UUID userId = authService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return taskMapper.toDTO(taskRepository.save(task.toBuilder()
                .user(user)
                .build()));
    }

    @Override
    public TaskDTO update(UUID id, TaskDTO taskDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        return taskMapper.toDTO(taskRepository.save(task.toBuilder()
                .title(taskDTO.title())
                .description(taskDTO.description())
                .updatedAt(LocalDateTime.now())
                .build()));
    }

    @Override
    public TaskDTO changeStatus(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        return taskMapper.toDTO(taskRepository.save(task.toBuilder()
                .isCompleted(!task.getIsCompleted())
                .build()));
    }
}
