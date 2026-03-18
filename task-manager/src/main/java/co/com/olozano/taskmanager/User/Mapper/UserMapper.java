package co.com.olozano.taskmanager.User.Mapper;

import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
