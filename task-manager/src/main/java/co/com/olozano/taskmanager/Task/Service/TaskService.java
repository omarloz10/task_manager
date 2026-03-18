package co.com.olozano.taskmanager.Task.Service;

import co.com.olozano.taskmanager.Task.DTO.TaskDTO;
import co.com.olozano.taskmanager.Task.Entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TaskService {

    List<TaskDTO> findAll();

    TaskDTO findById(UUID id);

    void deleteById(UUID id);

    TaskDTO save(TaskDTO taskDTO);

    TaskDTO update(UUID id, TaskDTO taskDTO);

    TaskDTO changeStatus(UUID taskId);


}
