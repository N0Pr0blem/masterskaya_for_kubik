package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.config.PasswordEncoderConfig;
import com.kubik.masterskaya.dto.cart.CartRequestDto;
import com.kubik.masterskaya.dto.user.UserRequestDto;
import com.kubik.masterskaya.entity.Role;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.UserRepository;
import com.kubik.masterskaya.service.CartService;
import com.kubik.masterskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final CartService cartService;

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
                        .cart(cartService.create())
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
                .map(user -> {
                    user.setRole(Role.ADMIN);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ApiException("No such user", "NO_SUCH_USER"));
    }

    @Override
    public User updateUser(UserRequestDto userRequestDto, User user) {
        if (userRequestDto.getUsername() != null) {
            Optional<User> findUser = userRepository.findByUsername(userRequestDto.getUsername());
            if (findUser.isEmpty()) user.setUsername(userRequestDto.getUsername());
        }
        if (userRequestDto.getPassword() != null) user.setPassword(
                passwordEncoderConfig.passwordEncoder()
                        .encode(userRequestDto.getPassword())
        );
        if (userRequestDto.getFName() != null) user.setFName(userRequestDto.getFName());
        if (userRequestDto.getSName() != null) user.setSName(userRequestDto.getSName());
        if (userRequestDto.getLName() != null) user.setLName(userRequestDto.getLName());
        if (userRequestDto.getEmail() != null) user.setEmail(userRequestDto.getEmail());
        if (userRequestDto.getPhoneNumber() != null) user.setPhoneNumber(userRequestDto.getPhoneNumber());
        if (userRequestDto.getBirthday() != null) user.setBirthday(userRequestDto.getBirthday());

        return userRepository.save(user);
    }
}

