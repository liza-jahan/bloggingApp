package com.example.bloggingapp.controllers;

import com.example.bloggingapp.payloads.UserDto;
import com.example.bloggingapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //Post-create  user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);

    }

    // PUT - Update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);

    }

    //Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<>(Map.of("message", "User Deleted Successfully"), HttpStatus.OK);
    }
    //GET - all user
}
