package com.example.bloggingapp.services;


import com.example.bloggingapp.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Optional;

public interface JwtService {

    String generateAccessToken(CustomUserDetails userDetails);

    String generateRefreshToken(CustomUserDetails userDetails);

    Jws<Claims> validateToken(String token);

    String createPasswordResetToken(Optional<String> user);

    String verifyPasswordResetToken(String token);
}
