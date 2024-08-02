package com.example.bloggingapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private  int id;
    private String name;
    private String password;
    private String email;
    private String about;

}
