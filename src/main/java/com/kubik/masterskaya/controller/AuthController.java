package com.kubik.masterskaya.controller;

import com.kubik.masterskaya.dto.auth.AuthenticationRequestDto;
import com.kubik.masterskaya.dto.auth.AuthenticationResponseDto;
import com.kubik.masterskaya.dto.auth.RegisterRequestDto;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.security.SecurityService;
import com.kubik.masterskaya.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final SecurityService securityService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<User> register( @RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerUser(new User().toBuilder()
                .username(registerRequestDto.getUsername())
                .password(registerRequestDto.getPassword())
                .build());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        String token = securityService.authenticate(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        return ResponseEntity.ok(authenticationResponseDto.toBuilder()
                .token(token)
                .username(authenticationRequestDto.getUsername())
                .build());
    }

    @PatchMapping("/up/{id}")
    @Operation(summary = "Make super user")
    public ResponseEntity<User> makeSuperUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.makeSuperUser(id));
    }

}