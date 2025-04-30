package com.youssef.GridPulse.configuration;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enable Spring Security @configuration & @EnableWebSecurity are mandatory in spring boot 3.0
@RequiredArgsConstructor
public class SecurityConfig {

    private final Filter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                    .authorizeHttpRequests(req -> req
                            .requestMatchers("/graphql").authenticated() // Require authentication for all requests to /graphql
                            .anyRequest().permitAll() // White Listing: Allow access to all other requests
                    )
                    .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to stateless
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before the UsernamePasswordAuthenticationFilter
//                    .httpBasic(AbstractHttpConfigurer::disable);// Disable basic authentication
                    .build();

    }
}