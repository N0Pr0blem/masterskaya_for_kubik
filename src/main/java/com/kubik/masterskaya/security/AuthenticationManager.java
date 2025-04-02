package com.kubik.masterskaya.security;

import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {
    private  final UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        User user =  userService.getUserById(principal.getId());
        if(user == null) {
            throw new RuntimeException("Authentication error");
        }
        if(!user.isEnabled()){
            throw new RuntimeException("User is disabled");
        }
        return authentication;
    }
}

