package com.example.bloggingapp.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private  int id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @Email
    private String email;
    private String about;

}
