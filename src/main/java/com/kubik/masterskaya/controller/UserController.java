package com.kubik.masterskaya.controller;

import com.kubik.masterskaya.dto.user.UserResponseDto;
import com.kubik.masterskaya.mapper.UserMapper;
import com.kubik.masterskaya.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{userId}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "userId") Long userId) {
        UserResponseDto userResponseDto = userMapper.toDto(userService.getUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping()
    @Operation(description = "Get all users")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (name != null && name.isEmpty()) {
            UserResponseDto userResponseDto = userMapper.toDto(userService.getUserByUsername(name));
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        } else {
            List<UserResponseDto> responseUsers = userMapper.toDtos(userService.getAllUsers(page, size));
            return ResponseEntity.status(HttpStatus.OK).body(responseUsers);
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "userId") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User with id - " + userId + " successful deleted");
    }

}
