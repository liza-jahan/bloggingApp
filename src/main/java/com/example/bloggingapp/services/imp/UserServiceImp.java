package com.example.bloggingapp.services.imp;

import com.example.bloggingapp.entites.User;
import com.example.bloggingapp.exception.ResourceNotFoundException;
import com.example.bloggingapp.payloads.UserDto;
import com.example.bloggingapp.repository.UserRepo;
import com.example.bloggingapp.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userdto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAbout(user.getAbout());

        User updateUser = this.userRepo.save(user);
        UserDto userDto = this.userToDto((updateUser));
        return userDto;
    }

    @Override
    public UserDto getUserId(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));


        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userdto) {
        User user=this.modelMapper.map(userdto,User.class);

         //  or

//        User user = new User();
//        user.setId(userdto.getId());
//        user.setName(userdto.getName());
//        user.setEmail(userdto.getEmail());
//        user.setPassword(userdto.getPassword());
//        user.setAbout(user.getAbout());

        return user;
    }

    public UserDto userToDto(User user) {

        UserDto userDto = this.modelMapper.map(user, UserDto.class);

     // or
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
