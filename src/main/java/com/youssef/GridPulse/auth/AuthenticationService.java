package com.youssef.GridPulse.auth;

import com.youssef.GridPulse.configuration.JwtService;
import com.youssef.GridPulse.token.Token;
import com.youssef.GridPulse.token.TokenRepository;
import com.youssef.GridPulse.token.TokenType;
import com.youssef.GridPulse.user.Role;
import com.youssef.GridPulse.user.User;
import com.youssef.GridPulse.user.UserRepository;
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
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    public AuthenticationResponse register(RegisterInput request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
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
        var user = repository.findByEmail(request.getEmail())
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
        var user = repository.findByEmail(email).orElseThrow();
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

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleEnum)
                .build();

        repository.save(user);

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

        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
