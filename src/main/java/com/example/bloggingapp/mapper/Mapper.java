package com.example.bloggingapp.mapper;


import com.example.bloggingapp.entites.Role;
import com.example.bloggingapp.entites.User;
import com.example.bloggingapp.model.CustomUserDetails;
import com.example.bloggingapp.payloads.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static CustomUserDetails toCustomUserDetails(User user){

        CustomUserDetails customUserDetails = new CustomUserDetails();
        BeanUtils.copyProperties(user, customUserDetails);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName().getValue()));

            r.getPermissions().forEach(p ->  {
                if(user.isVerified()){
                    authorities.add(new SimpleGrantedAuthority(p.getName()));
                }else if(!p.isRequiresVerification()){
                    authorities.add(new SimpleGrantedAuthority(p.getName()));
                }
            });
        });

        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }

//    public static UserDto UserResponse(User user) {
//        UserDto userResponse = new UserDto();
//        BeanUtils.copyProperties(user, userResponse, "roles");
//
//        List<Role> roles = new ArrayList<>();
//
//        user.getRoles().forEach(role -> {
//            Role roleResponse = new Role();
//            BeanUtils.copyProperties(role, roleResponse, "permissions");
//
//            List<String> permissions = new ArrayList<>();
//            role.getPermissions().forEach(p -> permissions.add(p.getName()));
//            roleResponse.setPermissions(permissions);
//            roles.add(roleResponse);
//        });
//
//        userResponse.setRoles(roles);
//        return userResponse;
//    }

}
