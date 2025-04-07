package com.kubik.masterskaya.service;


import com.kubik.masterskaya.dto.user.UserRequestDto;
import com.kubik.masterskaya.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    User getUserById(Long userId);

    User registerUser(User user);

    void deleteById(Long userId);

    List<User> getAllUsers(int page, int size);

    User makeSuperUser(Long id);

    User updateUser(UserRequestDto userRequestDto, User user);
}
