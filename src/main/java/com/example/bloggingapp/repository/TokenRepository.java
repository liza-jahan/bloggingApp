package com.example.bloggingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

@Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.revoked = false")
    List<Token> findByUserId(@Param("userId") UUID userId);

        Optional<Token> findByTokenAndRevoked(@Param("token") String token, boolean revoked);

@Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.tokenType = :tokenType AND t.revoked = false")
    Optional<Token> findByUserIdAndTokenType(@Param("userId") UUID userId, @Param("tokenType") TokenType tokenType);
        }
