package com.example.bloggingapp.repository;

import com.example.bloggingapp.enums.TokenType;
import com.example.bloggingapp.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

@Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.revoked = false")
List<Token> findByUserId(@Param("userId") UUID userId);

        Optional<Token> findByTokenAndRevoked(@Param("token") String token, boolean revoked);

@Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.tokenType = :tokenType AND t.revoked = false")
    Optional<Token> findByUserIdAndTokenType(@Param("userId") UUID userId, @Param("tokenType") TokenType tokenType);
        }
