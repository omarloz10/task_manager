package co.com.olozano.taskmanager.Task.Mapper;

import co.com.olozano.taskmanager.Task.DTO.TaskDTO;
import co.com.olozano.taskmanager.Task.Entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {


    TaskDTO toDTO(Task task);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    List<TaskDTO> toDTOs(List<Task> tasks);
}
