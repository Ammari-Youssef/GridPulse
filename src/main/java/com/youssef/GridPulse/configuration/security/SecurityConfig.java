package com.youssef.GridPulse.configuration.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enable Spring Security @configuration & @EnableWebSecurity are mandatory in spring boot 3.0
@EnableMethodSecurity // Enable method security annotations like @PreAuthorize, @PostAuthorize, etc.
@RequiredArgsConstructor
public class SecurityConfig {

    private final Filter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationCustomEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                    .authorizeHttpRequests(req -> req
                            .requestMatchers("/graphiql","/graphql").permitAll() // Require authentication for all requests to /graphql
                            .anyRequest().authenticated())

                    .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to stateless
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before the UsernamePasswordAuthenticationFilter
//                    .httpBasic(AbstractHttpConfigurer::disable);// Disable basic authentication

                    .exceptionHandling(ex -> ex
                            .authenticationEntryPoint(authEntryPoint)
                    ) // Set custom authentication entry point to handle unauthorized access
                    .build();

    }
}