package com.example.bloggingapp.config;

import com.example.bloggingapp.auth.CustomUserDetailsService;
import com.example.bloggingapp.auth.ExceptionHandlerFilter;
import com.example.bloggingapp.auth.JwtTokenVerifierFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;
    private final CustomUserDetailsService userDetailsService;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class).addFilterAfter(jwtTokenVerifierFilter, ExceptionHandlerFilter.class).authorizeHttpRequests(auth -> auth.requestMatchers
                ("/api/users", "/{userId}", "/", "/api/", "/swagger-ui/**",
                        "/user/{userId}/category/{categoryId}/posts", "/user/{userId}/posts", "/category/{categoryId}/posts", "/posts", "/posts/**",
                        "/deletePost/**", "/updatePost/**", "/posts/search/**",
                        "/post/image/upload/**", "/api-docs/**", "/swagger-ui/index.html?**")
                .permitAll()
                .anyRequest()
                .authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}


//
//    // Mark these fields as final to ensure they are injected via constructor
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrfConfigurer -> csrfConfigurer.disable())  // Disable CSRF protection
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/api/v1/auth/login").permitAll()  // Allow access to the login endpoint
//                        .anyRequest().authenticated())  // All other requests require authentication
//                .exceptionHandling(exceptionHandlingConfigurer ->
//                        exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))  // Use custom entry point
//                .sessionManagement(sessionManagementConfigurer ->
//                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session management
//                .httpBasic(withDefaults());  // Use HTTP Basic authentication with defaults
//
//        // Add JWT authentication filter
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//
//    @Bean
//    public AuthenticationManager authentication(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
////@Bean
////public  AuthenticationManager authenticationManager()throws Exception{
////        return  super.authenticationManager();
////}
//}
