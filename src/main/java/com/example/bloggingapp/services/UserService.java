package com.example.bloggingapp.services;

import com.example.bloggingapp.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserId(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);



}
