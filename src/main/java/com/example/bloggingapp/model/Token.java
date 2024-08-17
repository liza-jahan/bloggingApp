package com.example.bloggingapp.model;

import com.example.bloggingapp.entites.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Token is required")
    private String token;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Token type is required")
    private TokenType tokenType;

    private boolean revoked;

    @ManyToOne
    private User user;
}