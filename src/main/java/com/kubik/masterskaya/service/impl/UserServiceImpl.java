package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.config.PasswordEncoderConfig;
import com.kubik.masterskaya.entity.Role;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.UserRepository;
import com.kubik.masterskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("You dont have such user with name " + username));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("You dont have such user with id " + userId));
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(
                user.toBuilder()
                        .role(Role.USER)
                        .enabled(true)
                        .password(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()))
                        .username(user.getUsername())
                        .build()
        );
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers(int page, int size) {
        return userRepository.findAll();
    }

    @Override
    public User makeSuperUser(Long id) {
        return userRepository.findById(id)
                .map(user->{
                    user.setRole(Role.ADMIN);
                    return userRepository.save(user);
                })
                .orElseThrow(()->new ApiException("No such user","NO_SUCH_USER"));
    }
}

