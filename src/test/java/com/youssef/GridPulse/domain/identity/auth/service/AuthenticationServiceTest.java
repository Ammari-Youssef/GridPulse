package com.youssef.GridPulse.domain.identity.auth.service;

import com.youssef.GridPulse.configuration.security.JwtService;
import com.youssef.GridPulse.domain.identity.auth.dto.AuthenticationResponse;
import com.youssef.GridPulse.domain.identity.auth.dto.LoginInput;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.token.Token;
import com.youssef.GridPulse.domain.identity.token.TokenRepository;
import com.youssef.GridPulse.domain.identity.token.TokenType;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserHistoryRepository userHistoryRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthenticationService authService;

    @Captor
    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    @Captor
    private ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
//    @Captor
//    private ArgumentCaptor<UserHistory> historyCaptor; // removing this breaks the tests

    private User testUser;
    private final UUID testUserId = UUID.randomUUID();

    // Test counter
    private static int testCounter = 1;

    @BeforeAll
    static void start() {
        System.out.println("--------------------------- AuthenticationService start ---------------------------------------\n");
    }

    @AfterAll
    static void finish() {
        System.out.println("\n ------------------------------ AuthenticationService end ---------------------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("******************************* AuthenticationService TEST " + testCounter + " TEARDOWN *************************************");
        testCounter++;
    }

    @BeforeEach
    void setUp() {
        System.out.println("******************************* AuthenticationService TEST " + testCounter + " SETUP **********************************");
        testUser = User.builder()
                .id(testUserId)
                .email("test@example.com")
                .password("encodedPassword")
                .firstname("John")
                .lastname("Doe")
                .role(Role.USER)
                .enabled(true)
                .createdAt(Instant.now())
                .build();

    }

    // ========== REGISTER TESTS ==========
    @Nested
    @DisplayName("Register Tests")
    class RegisterTests {
        @Test
        void register_ValidRequest_ShouldSaveUserCreateHistoryAndReturnTokens() {
            // GIVEN
            RegisterInput registerInput = RegisterInput.builder()
                    .email("new@example.com")
                    .password("plainPassword")
                    .firstname("Jane")
                    .lastname("Smith")
                    .build();

            User savedUser = User.builder()
                    .id(UUID.randomUUID())
                    .email("new@example.com")
                    .password("encodedPassword")
                    .firstname("Jane")
                    .lastname("Smith")
                    .role(Role.USER)
                    .enabled(true)
                    .build();

            UserHistory userHistory = UserHistory.builder()
                    .originalId(savedUser.getId())
                    .email("new@example.com")
                    .password("encodedPassword")
                    .firstname("Jane")
                    .lastname("Smith")
                    .role("USER")
                    .enabled(true)
                    .createdRecord(true)
                    .build();

            when(userMapper.toHistory(savedUser)).thenReturn(userHistory);
            when(userMapper.toEntity(registerInput)).thenReturn(savedUser);
            when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
            when(jwtService.generateToken(savedUser)).thenReturn("access-token");
            when(jwtService.generateRefreshToken(savedUser)).thenReturn("refresh-token");


            when(userRepository.save(savedUser)).thenReturn(savedUser);
            when(userHistoryRepository.save(userHistory)).thenReturn(userHistory);
            when(tokenRepository.save(any(Token.class))).thenAnswer(inv -> inv.getArgument(0));

            // WHEN
            AuthenticationResponse response = authService.register(registerInput);

            // THEN
            assertThat(response.getAccessToken()).isEqualTo("access-token");
            assertThat(response.getRefreshToken()).isEqualTo("refresh-token");

            verify(userMapper).toEntity(registerInput);
            verify(passwordEncoder).encode("plainPassword");

            verify(userRepository).save(userCaptor.capture());
            User capturedUser = userCaptor.getValue();
            assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");
            assertThat(capturedUser.isEnabled()).isTrue();

            verify(userMapper).toHistory(savedUser);
            verify(userHistoryRepository).save(userHistory);

            verify(jwtService).generateToken(savedUser);
            verify(jwtService).generateRefreshToken(savedUser);
            verify(tokenRepository, times(2)).save(any(Token.class));
        }
    }

    // ========== AUTHENTICATE TESTS ==========
    @Nested
    @DisplayName("Login Tests")
    class LoginTests {
        @Test
        void authenticate_ValidCredentials_ShouldReturnTokensAndRevokeOldOnes() {
            // GIVEN
            LoginInput loginInput = LoginInput.builder()
                    .email("test@example.com")
                    .password("password123")
                    .build();

            Authentication authentication = mock(Authentication.class);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);
            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
            when(jwtService.generateToken(testUser)).thenReturn("access-token");
            when(jwtService.generateRefreshToken(testUser)).thenReturn("refresh-token");
            when(tokenRepository.findAllValidTokenByUser(testUserId, TokenType.BEARER))
                    .thenReturn(List.of());

            // WHEN
            AuthenticationResponse response = authService.authenticate(loginInput);

            // THEN
            assertThat(response.getAccessToken()).isEqualTo("access-token");
            assertThat(response.getRefreshToken()).isEqualTo("refresh-token");

            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(userRepository).findByEmail("test@example.com");
            verify(jwtService).generateToken(testUser);
            verify(jwtService).generateRefreshToken(testUser);
            verify(tokenRepository, times(2)).save(any(Token.class));
        }

        @Test
        void authenticate_InvalidCredentials_ShouldThrowException() {
            // GIVEN
            LoginInput loginInput = LoginInput.builder()
                    .email("test@example.com")
                    .password("wrongPassword")
                    .build();

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Bad credentials"));

            // WHEN & THEN
            assertThrows(BadCredentialsException.class, () -> authService.authenticate(loginInput));
        }
    }

    // ========== REFRESH TOKEN TESTS ==========

    @Nested
    @DisplayName("Refresh Token Tests")
    class RefreshTokenTests {
        @Test
        void refreshToken_ValidToken_ShouldReturnNewAccessToken() {
            // GIVEN
            String refreshToken = "valid-refresh-token";
            Token validToken = Token.builder()
                    .token(refreshToken)
                    .expired(false)
                    .revoked(false)
                    .build();

            when(tokenRepository.findByToken(refreshToken)).thenReturn(Optional.of(validToken));
            when(jwtService.extractUsername(refreshToken)).thenReturn("test@example.com");
            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
            when(jwtService.generateToken(testUser)).thenReturn("new-access-token");
            when(tokenRepository.findAllValidTokenByUser(testUserId, TokenType.BEARER))
                    .thenReturn(List.of());

            // WHEN
            AuthenticationResponse response = authService.refreshToken(refreshToken);

            // THEN
            assertThat(response.getAccessToken()).isEqualTo("new-access-token");
            assertThat(response.getRefreshToken()).isEqualTo(refreshToken);

            verify(tokenRepository).findByToken(refreshToken);
            verify(jwtService).extractUsername(refreshToken);
            verify(userRepository).findByEmail("test@example.com");
            verify(jwtService).generateToken(testUser);
            verify(tokenRepository).save(any(Token.class));
        }

        @Test
        void refreshToken_InvalidToken_ShouldThrowException() {
            // GIVEN
            String invalidToken = "invalid-token";
            when(tokenRepository.findByToken(invalidToken)).thenReturn(Optional.empty());

            // WHEN & THEN
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> authService.refreshToken(invalidToken));
            assertThat(exception.getMessage()).contains("Invalid refresh token");
        }

        @Test
        void refreshToken_ExpiredToken_ShouldThrowException() {
            // GIVEN
            String expiredToken = "expired-token";
            Token token = Token.builder()
                    .token(expiredToken)
                    .expired(true)
                    .revoked(false)
                    .build();

            when(tokenRepository.findByToken(expiredToken)).thenReturn(Optional.of(token));

            // WHEN & THEN
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> authService.refreshToken(expiredToken));
            assertThat(exception.getMessage()).contains("Invalid refresh token");
        }
    }
    // ========== LOGOUT TESTS ==========

    @Nested
    @DisplayName("Logout Tests")
    class LogoutTests {
        @Test
        void logout_ValidToken_ShouldRevokeTokenAndClearContext() {
            // GIVEN
            String jwtToken = "valid-jwt-token";
            Token token = Token.builder()
                    .token(jwtToken)
                    .expired(false)
                    .revoked(false)
                    .build();

            when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
            when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(token));

            // Create a mock SecurityContext to verify it gets cleared
            SecurityContext securityContext = mock(SecurityContext.class);
            SecurityContextHolder.setContext(securityContext);

            // WHEN
            boolean result = authService.logout();

            // THEN
            assertTrue(result);
            verify(tokenRepository).save(tokenCaptor.capture());
            Token savedToken = tokenCaptor.getValue();
            assertThat(savedToken).isEqualTo(token);
            assertTrue(savedToken.isExpired());
            assertTrue(savedToken.isRevoked());

            // Verify that the security context was cleared
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        void logout_InvalidAuthHeader_ShouldReturnFalse() {
            // GIVEN
            when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

            // WHEN
            boolean result = authService.logout();

            // THEN
            assertFalse(result);
            verify(tokenRepository, never()).save(any());
        }

        @Test
        void logout_TokenNotFound_ShouldReturnFalse() {
            // GIVEN
            when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
            when(tokenRepository.findByToken("invalid-token")).thenReturn(Optional.empty());

            // WHEN
            boolean result = authService.logout();

            // THEN
            assertFalse(result);
            verify(tokenRepository, never()).save(any());
        }
    }
    // ========== CREATE USER WITH ROLE TESTS ==========

    @Nested
    @DisplayName("CreateUserWithRole Tests")
    class CreateUserWithRoleTests {
        @Test
        void createUserWithRole_ValidRequest_ShouldCreateUserWithSpecifiedRole() {
            // GIVEN
            RegisterInput registerInput = RegisterInput.builder()
                    .email("admin@example.com")
                    .password("adminPass")
                    .firstname("Admin")
                    .lastname("User")
                    .build();

            User savedUser = User.builder()
                    .id(UUID.randomUUID())
                    .email("admin@example.com")
                    .password("encodedPassword")
                    .firstname("Admin")
                    .lastname("User")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            UserHistory userHistory = UserHistory.builder()
                    .originalId(savedUser.getId())
                    .email("admin@example.com")
                    .firstname("Admin")
                    .lastname("User")
                    .role("ADMIN")
                    .enabled(true)
                    .createdRecord(true)
                    .build();

            when(userMapper.toEntity(registerInput)).thenReturn(savedUser);
            when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(savedUser);
            when(userMapper.toHistory(savedUser)).thenReturn(userHistory);
            when(userHistoryRepository.save(userHistory)).thenReturn(userHistory);
            when(jwtService.generateToken(savedUser)).thenReturn("admin-access-token");
            when(jwtService.generateRefreshToken(savedUser)).thenReturn("admin-refresh-token");
            when(tokenRepository.findAllValidTokenByUser(savedUser.getId(), TokenType.BEARER))
                    .thenReturn(List.of());
            when(tokenRepository.save(any(Token.class))).thenAnswer(inv -> inv.getArgument(0));

            // WHEN
            AuthenticationResponse response = authService.createUserWithRole(registerInput, "ADMIN");

            // THEN
            assertThat(response.getAccessToken()).isEqualTo("admin-access-token");
            assertThat(response.getRefreshToken()).isEqualTo("admin-refresh-token");

            verify(userRepository).save(userCaptor.capture());
            User capturedUser = userCaptor.getValue();
            assertThat(capturedUser.getRole()).isEqualTo(Role.ADMIN);
            assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");
            assertThat(capturedUser.isEnabled()).isTrue();

            verify(userMapper).toHistory(savedUser);
            verify(userHistoryRepository).save(userHistory);

            verify(jwtService).generateToken(savedUser);
            verify(jwtService).generateRefreshToken(savedUser);
            verify(tokenRepository, times(2)).save(any(Token.class));

        }

        @Test
        void createUserWithRole_InvalidRole_ShouldThrowException() {
            // GIVEN
            RegisterInput registerInput = RegisterInput.builder()
                    .email("test@example.com")
                    .password("password")
                    .build();

            // WHEN & THEN
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> authService.createUserWithRole(registerInput, "INVALID_ROLE"));
            assertThat(exception.getMessage()).contains("Invalid role");
        }
    }
    // ========== GET CURRENT USER TESTS ==========
    @Nested
    @DisplayName("GetCurrentUser Tests")
    class GetCurrentUser {
        @Test
        void getCurrentUser_AuthenticatedUser_ShouldReturnUser() {
            // GIVEN
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getName()).thenReturn("test@example.com");
            SecurityContextHolder.setContext(securityContext);

            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

            // WHEN
            User result = authService.getCurrentUser();

            // THEN
            assertThat(result).isEqualTo(testUser);
            verify(userRepository).findByEmail("test@example.com");
        }

        @Test
        void getCurrentUser_NotAuthenticated_ShouldThrowException() {
            // GIVEN
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(null);
            SecurityContextHolder.setContext(securityContext);

            // WHEN & THEN
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> authService.getCurrentUser());
            assertThat(exception.getMessage()).contains("No user is currently authenticated");
        }

        @Test
        void getCurrentUser_UserNotFound_ShouldThrowException() {
            // GIVEN
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getName()).thenReturn("nonexistent@example.com");
            SecurityContextHolder.setContext(securityContext);

            when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

            // WHEN & THEN
            assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                    () -> authService.getCurrentUser());
        }
    }
    // ========== HELPER METHOD TESTS ==========


    @Test
    void register_ShouldCallSaveUserToken() {
        // GIVEN
        RegisterInput registerInput = RegisterInput.builder()
                .email("test@example.com")
                .password("plainPassword")
                .firstname("Test")
                .lastname("User")
                .build();

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("encodedPassword")
                .firstname("Test")
                .lastname("User")
                .role(Role.USER)
                .enabled(true)
                .build();

        UserHistory history = UserHistory.builder()
                .originalId(savedUser.getId())
                .email("test@example.com")
                .password("encodedPassword")
                .firstname("Test")
                .lastname("User")
                .role("USER")
                .enabled(true)
                .build();

        when(userMapper.toEntity(registerInput)).thenReturn(savedUser);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(userMapper.toHistory(savedUser)).thenReturn(history);
        when(userHistoryRepository.save(history)).thenReturn(history);
        when(jwtService.generateToken(savedUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(savedUser)).thenReturn("refresh-token");
        when(tokenRepository.save(any(Token.class))).thenAnswer(inv -> inv.getArgument(0));

        // WHEN
        AuthenticationResponse response = authService.register(registerInput);

        // THEN - Verify that saveUserToken was called (indirectly through tokenRepository.save)
        verify(tokenRepository, times(2)).save(tokenCaptor.capture());
        verify(userRepository).save(userCaptor.capture()); // Save user once
        List<Token> savedTokens = tokenCaptor.getAllValues();


        // Verify response
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");

        // Verify access token
        Token accessToken = savedTokens.get(0);
        assertThat(accessToken.getUser()).isEqualTo(savedUser);
        assertThat(accessToken.getToken()).isEqualTo("access-token");
        assertThat(accessToken.getTokenType()).isEqualTo(TokenType.BEARER);
        assertThat(accessToken.isExpired()).isFalse();
        assertThat(accessToken.isRevoked()).isFalse();

        // Verify refresh token
        Token refreshToken = savedTokens.get(1);
        assertThat(refreshToken.getUser()).isEqualTo(savedUser);
        assertThat(refreshToken.getToken()).isEqualTo("refresh-token");
        assertThat(refreshToken.getTokenType()).isEqualTo(TokenType.REFRESH);
        assertThat(refreshToken.isExpired()).isFalse();
        assertThat(refreshToken.isRevoked()).isFalse();
    }

    @Test
    void authenticate_ShouldRevokeAllPreviousTokens() {
        // GIVEN
        LoginInput loginInput = LoginInput.builder()
                .email(testUser.getEmail())
                .password("rawPassword")
                .build();

        Token validToken1 = Token.builder()
                .token("old-token-1")
                .expired(false)
                .revoked(false)
                .build();

        Token validToken2 = Token.builder()
                .token("old-token-2")
                .expired(false)
                .revoked(false)
                .build();

        // Mock dependencies
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(testUser)).thenReturn("refresh-token");
        when(tokenRepository.save(any(Token.class))).thenAnswer(inv -> inv.getArgument(0));

        // Mock token repository to return the old tokens
        when(tokenRepository.findAllValidTokenByUser(testUser.getId(), TokenType.BEARER))
                .thenReturn(List.of(validToken1, validToken2));

        // Use ArgumentCaptor for saveAll
        ArgumentCaptor<List<Token>> saveAllCaptor = ArgumentCaptor.forClass(List.class);

        // WHEN
        authService.authenticate(loginInput);

        // THEN - Verify that saveAll was called with revoked tokens
        verify(tokenRepository).saveAll(saveAllCaptor.capture());
        List<Token> revokedTokens = saveAllCaptor.getValue();

        assertThat(revokedTokens).hasSize(2);
        assertThat(revokedTokens).allMatch(Token::isExpired);
        assertThat(revokedTokens).allMatch(Token::isRevoked);

        // Also verify new tokens were created
        verify(tokenRepository, times(2)).save(any(Token.class));
    }
}