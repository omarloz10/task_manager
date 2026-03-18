package co.com.olozano.taskmanager.User.Service;

import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    UserDto save(UserDto user);

    UserDto findById(UUID id);

    User findByEmail(String email);

    UserDto update(UUID id, UserDto userDto);
}
