package com.example.bloggingapp.services.imp;

import com.example.bloggingapp.config.AuthenticationProperties;
import com.example.bloggingapp.exception.TimeExistException;
import com.example.bloggingapp.exception.TokenExpiredException;
import com.example.bloggingapp.model.CustomUserDetails;
import com.example.bloggingapp.repository.TokenRepository;
import com.example.bloggingapp.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.*;

@Slf4j
@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final AuthenticationProperties authenticationProperties;

    private final TokenRepository tokenRepository;

    @Override
    public String generateAccessToken(CustomUserDetails userDetails) {

        List<SimpleGrantedAuthority> authoritiesList = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();

        String authorities = authoritiesList.stream().map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Map<String, Object> tokenBody = new HashMap<>();
        tokenBody.put("id", userDetails.getId());
        tokenBody.put("firstName", userDetails.getFirstName());
        tokenBody.put("lastName", userDetails.getLastName());
        tokenBody.put("username", userDetails.getUsername());
        tokenBody.put("phoneNumber", userDetails.getPhoneNumber());
        tokenBody.put("verified", userDetails.isVerified());
        tokenBody.put("authorities", authorities);

        byte[] key = authenticationProperties.getSecretKey().getBytes();
        Integer expiration = authenticationProperties.getAccessToken().getExpirationInSeconds();

        return this.buildJwt(tokenBody, expiration, key);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails) {

        Map<String, Object> tokenBody = new HashMap<>();
        tokenBody.put("id", userDetails.getId());
        tokenBody.put("username", userDetails.getUsername());

        byte[] key = authenticationProperties.getSecretKey().getBytes();
        Integer expiration = authenticationProperties.getRefreshToken().getExpirationInSeconds();

        return this.buildJwt(tokenBody, expiration, key);
    }

    @Override
    public Jws<Claims> validateToken(String token) {

        tokenRepository.findByTokenAndRevoked(token, false)
                .orElseThrow(() -> new JwtException("Token is invalid"));

        byte[] key = authenticationProperties.getSecretKey().getBytes();

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key))
                .build().parseClaimsJws(token);
    }

    @Override
    public String createPasswordResetToken(Optional<String> user) {

        Map<String, Object> body = new HashMap<>();
        body.put("userName", user.get());
        byte[] key = authenticationProperties.getSecretKey().getBytes();

        return buildJwt(body, 60 * 15, key);
    }

    @Override
    public String verifyPasswordResetToken(String token) {

        Jws<Claims> jwsClaims = null;
        byte[] key = authenticationProperties.getSecretKey().getBytes();
        try {
            jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(key))
                    .build().parseClaimsJws(token);

        } catch (ExpiredJwtException expiredJwtException) {
            log.info("Token has expired: {}", expiredJwtException.getMessage());
            throw new TokenExpiredException("Token has expired", expiredJwtException);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            log.info("token parssing fail {}", e.getMessage());
            throw new TimeExistException(e.getMessage(), "1900-39-9");
        }
        Claims claims = jwsClaims.getBody();
        String email = claims.get("userName", String.class);


        return email;
    }


    private String buildJwt(Map<String, Object> body, Integer expiration, byte[] signingKey) {

        try {
            return builder()
                    .addClaims(body)
                    .setIssuedAt(new Date())
                    .setIssuer("self")
                    .setSubject((String) body.get("username"))
                    .setExpiration(java.sql.Timestamp.valueOf(LocalDateTime.now()
                            .plus(expiration, ChronoUnit.SECONDS)))
                    .signWith(Keys.hmacShaKeyFor(signingKey))
                    .compact();

        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }
}