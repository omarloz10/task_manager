package co.com.olozano.taskmanager.User.Controller;

import co.com.olozano.taskmanager.User.DTO.UserDto;
import co.com.olozano.taskmanager.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable(name = "userId") UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userDto));
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable(name = "userId") UUID id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.update(id, userDto));
    }

}
