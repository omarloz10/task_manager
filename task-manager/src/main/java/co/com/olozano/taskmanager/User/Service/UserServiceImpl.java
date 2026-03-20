package co.com.olozano.taskmanager.User.Service;

import co.com.olozano.taskmanager.Exception.Exceptions.NotFoundException;
import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Entity.User;
import co.com.olozano.taskmanager.User.Mapper.UserMapper;
import co.com.olozano.taskmanager.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto userDto) {
        User userDB = userMapper.toEntity(userDto);

        return userMapper.toDto(userRepository.save(userDB.toBuilder()
                .password(passwordEncoder.encode(userDB.getPassword()))
                .build()));
    }

    @Override
    public UserDto findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new NotFoundException("User not found!");
        }

        return user;
    }

    @Override
    public UserDto update(UUID id, UserDto userDto) {

        User userDB = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        return userMapper.toDto(userRepository.save(userDB.toBuilder()
                .name(userDto.name())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDB.getPassword()))
                .build()));
    }
}
