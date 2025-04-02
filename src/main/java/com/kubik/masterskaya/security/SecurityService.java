package com.kubik.masterskaya.security;

import com.kubik.masterskaya.config.PasswordEncoderConfig;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public String authenticate(String username, String password) {
        User user = userService.getUserByUsername(username);
        if(!user.isEnabled()){
            throw new RuntimeException("Account disabled");
        }

        if(!passwordEncoderConfig.passwordEncoder().matches(password,user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtTokenProvider.generateToken(username);
    }
}
