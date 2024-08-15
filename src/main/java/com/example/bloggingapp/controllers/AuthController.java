package com.example.bloggingapp.controllers;

import com.example.bloggingapp.payloads.JwtAuthRequest;
import com.example.bloggingapp.payloads.JwtResponse;
import com.example.bloggingapp.security.JwtTokenHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/v1/auth/")
//@AllArgsConstructor
public class AuthController {
//    private final JwtTokenHelper jwtTokenHelper;
//    private final UserDetailsService userDetailsService;
//    private final AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
//
//        this.authenticate(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//        JwtResponse response = new JwtResponse();
//        response.setToken(token);
//        return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
//    }
//
//    private void authenticate(String email, String password) {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//    }
}
