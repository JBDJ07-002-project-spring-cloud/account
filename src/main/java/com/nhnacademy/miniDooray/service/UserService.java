package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.DTO.UserPartialUpdateRequest;
import com.nhnacademy.miniDooray.DTO.UserRegistrationRequest;
import com.nhnacademy.miniDooray.DTO.UserRequest;
import com.nhnacademy.miniDooray.DTO.UserUpdateRequest;
import com.nhnacademy.miniDooray.entity.User;

import java.util.List;

public interface UserService {
    User registUser(UserRegistrationRequest userRequest);
    User getUser(Long userId);
    List<User> getAllUsers();
    void updateUser(UserUpdateRequest userRequest);
    void partialUpdateUser(Long userId, UserPartialUpdateRequest userRequest);
    void deleteUser(Long userId);

    boolean comparePassword(String rawPassword, String storedEncryptedPassword);
}
