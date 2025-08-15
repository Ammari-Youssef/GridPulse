package com.youssef.GridPulse.domain.identity.auth.service;

import com.youssef.GridPulse.configuration.security.JwtService;
import com.youssef.GridPulse.domain.identity.auth.dto.AuthenticationResponse;
import com.youssef.GridPulse.domain.identity.auth.dto.LoginInput;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.*;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.domain.identity.token.Token;
import com.youssef.GridPulse.domain.identity.token.TokenRepository;
import com.youssef.GridPulse.domain.identity.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final UserMapper userMapper;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    public AuthenticationResponse register(RegisterInput request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        var savedUser = userRepository.save(user);

        // Save audit history
        UserHistory history = userMapper.toHistory(savedUser);
        history.setOriginalId(savedUser.getId());
        history.setCreatedRecord(true);
        userHistoryRepository.save(history);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user); // Generate a refresh token for the authenticated user
        saveUserToken(savedUser, jwtToken, TokenType.BEARER);
        saveUserToken(savedUser, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(LoginInput request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user); // Generate a refresh token for the authenticated user
        revokeAllUserTokens(user); // Revoke all previous tokens for the user
        // Save the new tokens for the user
        saveUserToken(user, jwtToken, TokenType.BEARER);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        var isTokenValid = tokenRepository.findByToken(refreshToken)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
        if (!isTokenValid) {
            throw new RuntimeException("Invalid refresh token");
        }
        var email = jwtService.extractUsername(refreshToken);
        var user = userRepository.findByEmail(email).orElseThrow();
        var newJwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, newJwtToken, TokenType.BEARER);

        return AuthenticationResponse.builder()
                .accessToken(newJwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean logout() {

        final String authHeader = this.request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return false;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            return true;
        }
        return false;
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId(), TokenType.BEARER);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse createUserWithRole(RegisterInput request, String role) {
        Role roleEnum;
        try {
            roleEnum = Role.valueOf(role.toUpperCase()); // ensure case insensitivity
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        var user = userMapper.toEntity(request);
        user.setRole(roleEnum);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepository.save(user);

        // Save audit history
        UserHistory history = userMapper.toHistory(savedUser);
        history.setOriginalId(savedUser.getId());
        history.setCreatedRecord(true);
        userHistoryRepository.save(history);

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user); // Revoke all previous access tokens for the user
        saveUserToken(user, jwtToken, TokenType.BEARER);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No user is currently authenticated");
        }

        String email = authentication.getName(); // or username

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}
