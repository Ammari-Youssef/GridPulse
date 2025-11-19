package com.youssef.GridPulse.configuration.security;

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
public class ApiSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationCustomEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**") // matches all API endpoints except those handled by ActuatorSecurity
                .authorizeHttpRequests(req -> req
                        // PUBLIC ENDPOINTS
                        .requestMatchers(
                                "/graphiql",
                                "/graphiql/**",
                                "/graphql",
                                "/graphql/**"
                        ).permitAll()

                        // EVERYTHING ELSE IS SECURED
                        .anyRequest().authenticated())

                .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)

                // ADD JWT FILTER
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .httpBasic(AbstractHttpConfigurer::disable)

                .build();
    }

}