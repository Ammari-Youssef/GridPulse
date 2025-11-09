package com.youssef.GridPulse.domain.identity.auth.resolver;

import com.youssef.GridPulse.domain.identity.auth.dto.AuthenticationResponse;
import com.youssef.GridPulse.domain.identity.auth.dto.LoginInput;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.auth.service.AuthenticationService;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;

import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQlTest(AuthenticationResolver.class)
@EnableMethodSecurity
@AutoConfigureGraphQlTester
class AuthenticationResolverTest {

    @Autowired
    private GraphQlTester graphQlTest;

    @MockitoBean
    AuthenticationService service;

    private static Instant suiteStartTime;
    private static int testCounter = 1;

    private RegisterInput registerInput;

    private AuthenticationResponse authResponse;

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        System.out.println("\n⭐ Authenticationservice Test Execution Started");
        System.out.println("⏰ Start Time: " + suiteStartTime);
        System.out.println("-".repeat(50));
    }

    @AfterAll
    static void endTestExecution() {
        Instant suiteEndTime = Instant.now();
        long duration = java.time.Duration.between(suiteStartTime, suiteEndTime).toMillis();

        System.out.println("-".repeat(50));
        System.out.println("🏁 Authenticationservice Test Execution Completed");
        System.out.println("⏰ End Time: " + suiteEndTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(50));
    }

    @BeforeEach
    void setUp() {
        System.out.println("📋 Test " + testCounter + " - Setting up...");
        authResponse = AuthenticationResponse.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .build();
        registerInput = RegisterInput.builder()
                .email("john.doe@example.com")
                .password("password123")
                .firstname("John")
                .lastname("Doe")
                .build();

    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Test " + testCounter + " - Completed");
        testCounter += 1;
    }

    // ========== REGISTER TESTS ==========

    @Nested
    @DisplayName("REGISTER TESTS")
    class RegisterTest {
        @Test
        void register_Success() {
            // Use the properly initialized field
            when(service.register(any(RegisterInput.class))).thenReturn(authResponse);

            graphQlTest.documentName("mutations/auth/register")
                    .variable("registerInput", Map.of(
                            "email", registerInput.getEmail(),
                            "password", registerInput.getPassword(),
                            "firstname", registerInput.getFirstname(),
                            "lastname", registerInput.getLastname()
                    ))
                    .execute()
                    .path("register.accessToken")
                    .entity(String.class)
                    .isEqualTo("access-token")
                    .path("register.refreshToken")
                    .entity(String.class)
                    .isEqualTo("refresh-token");

            verify(service).register(any(RegisterInput.class));
        }

        @Test
        @DisplayName("register - Should return error when email already exists")
        void register_Fail() {
            // given - Mock service to throw duplicate email exception
            when(service.register(any(RegisterInput.class)))
                    .thenThrow(new RuntimeException());

            // when & then
            graphQlTest.documentName("mutations/auth/register")
                    .variable("registerInput", Map.of(
                            "email", "john.doe@example.com",
                            "password", "password123",
                            "firstname", "John",
                            "lastname", "Doe"
                    ))
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR");
                    });

            verify(service).register(any(RegisterInput.class));
        }
    }

    // ========== LOGIN TESTS ==========
    @Nested
    @DisplayName("LOGIN TESTS")
    class LoginTests {
        @Test
        void login() {
            when(service.authenticate(any(LoginInput.class))).thenReturn(authResponse);

            graphQlTest.documentName("mutations/auth/login")
                    .variable("email", "john.doe@example.com")
                    .variable("password", "password123")
                    .execute()
                    .path("login.accessToken")
                    .entity(String.class)
                    .isEqualTo("access-token")
                    .path("login.refreshToken")
                    .entity(String.class)
                    .isEqualTo("refresh-token");

            verify(service).authenticate(any());
        }
    }

    // ========== LOGOUT TESTS ==========
    @Nested
    @DisplayName("LOGOUT TESTS")
    class LogoutTests {
        @Test
        @DisplayName("logout - Should return true when service returns true")
        @WithMockUser
        void logout_Success() {
            // given - Service returns success
            when(service.logout()).thenReturn(true);

            // when & then - Test GraphQL response
            graphQlTest.documentName("mutations/auth/logout")
                    .execute()
                    .path("logout")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            // verify service was called
            verify(service).logout();
        }


        @Test
        @WithAnonymousUser
        @DisplayName("logout - Should return error when not authenticated")
        void logout_WhenNotAuthenticated_Fails() {
            // Ensure no authentication
            SecurityContextHolder.clearContext();

            // when & then - Test GraphQL response
            graphQlTest.documentName("mutations/auth/logout")
                    .execute()
                    .errors()
                    .satisfy(error -> {
                        assertThat(error).hasSize(1);
                        assertThat(error.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            // Verify the service was NEVER called since authentication fails first
            verify(service, never()).logout();
        }
    }

    // ========== REFRESH TOKEN TESTS ==========

    @Nested
    @DisplayName("REFRESH TOKEN TESTS")
    class RefreshTokenTests {

        @Test
        void refreshToken_success() {
            // given
            String newAccessToken = "new-access-token";
            authResponse.setAccessToken(newAccessToken);
            // when
            when(service.refreshToken(authResponse.getRefreshToken())).thenReturn(authResponse);
            // then
            graphQlTest.documentName("mutations/auth/refreshToken")
                    .variable("token", authResponse.getRefreshToken())
                    .execute()
                    .path("refreshToken.accessToken")
                    .entity(String.class)
                    .isEqualTo(newAccessToken)
                    .path("refreshToken.refreshToken")
                    .entity(String.class)
                    .isEqualTo("refresh-token");

            verify(service).refreshToken(authResponse.getRefreshToken());
        }

        @Test
        @DisplayName("refreshToken - Should return error when token is invalid")
        void refreshToken_Fail() {
            //given
            when(service.refreshToken(authResponse.getRefreshToken())).thenThrow(new RuntimeException());

            // when & then
            graphQlTest.documentName("mutations/auth/refreshToken")
                    .variable("token", authResponse.getRefreshToken())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR");
                    });
            verify(service).refreshToken(authResponse.getRefreshToken());
        }
    }
    // ========== CREATE WITH ROLE TESTS ==========

    @Nested
    @DisplayName("CREATE WITH ROLE TESTS")
    class CreateUserWithRoleTests {

        @Test
        @WithMockUser(roles = {"ADMIN"})
        void createUserWithRole_success() {
            // given
            when(service.createUserWithRole(registerInput, "ADMIN")).thenReturn(authResponse);

            // when & then
            graphQlTest.documentName("mutations/auth/createUserWithRole")
                    .variable("registerInput", Map.of(
                            "email", registerInput.getEmail(),
                            "password", registerInput.getPassword(),
                            "firstname", registerInput.getFirstname(),
                            "lastname", registerInput.getLastname()
                    ))
                    .variable("role", "ADMIN")
                    .execute()
                    .path("createUserWithRole.accessToken")
                    .entity(String.class)
                    .isEqualTo("access-token")
                    .path("createUserWithRole.refreshToken")
                    .entity(String.class)
                    .isEqualTo("refresh-token");

            verify(service).createUserWithRole(registerInput, "ADMIN");
        }

        @Test
        @WithMockUser(roles = "USER")
        void createUserWithRole_ShouldFailWhenNotAdmin() {
            // when & then - should get UNAUTHORIZED error, and the service is never called
            graphQlTest.documentName("mutations/auth/createUserWithRole")
                    .variable("registerInput", Map.of(
                            "email", registerInput.getEmail(),
                            "password", registerInput.getPassword(),
                            "firstname", registerInput.getFirstname(),
                            "lastname", registerInput.getLastname()
                    ))
                    .variable("role", "ADMIN")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");

                    });

            // Verify service method was NEVER called (security should block it)
            verify(service, never()).createUserWithRole(any(RegisterInput.class), eq("ADMIN"));
        }

        @Test
        @WithAnonymousUser
        void createUserWithRole_ShouldFailWhenAnonymous() {
            // when & then - should get UNAUTHORIZED error, and the service is never called
            graphQlTest.documentName("mutations/auth/createUserWithRole")
                    .variable("registerInput", Map.of(
                            "email", registerInput.getEmail(),
                            "password", registerInput.getPassword(),
                            "firstname", registerInput.getFirstname(),
                            "lastname", registerInput.getLastname()
                    ))
                    .variable("role", "ADMIN")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");

                    });

            // Verify service method was NEVER called (security should block it)
            verify(service, never()).createUserWithRole(any(RegisterInput.class), eq("ADMIN"));
        }
    }

    // ========== GET CURRENT TESTS ==========
    @Nested
    @DisplayName("GET CURRENT USER TESTS")
    class GetCurrentUserTests {

        @Test
        @WithAnonymousUser
        void getCurrentUser_Fail() {
            // when & then
            graphQlTest.documentName("queries/user/getCurrentUser")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR");
                    });

            verify(service, never()).getCurrentUser();
        }

        @Test
        @WithMockUser
        void getCurrentUser_Success() {
            // given
            var user = User.builder()
                    .id(UUID.randomUUID())
                    .email("test@example.com")
                    .password("encodedPassword")
                    .firstname("John")
                    .lastname("Doe")
                    .role(Role.USER)
                    .enabled(true)
                    .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                    .build();

            when(service.getCurrentUser()).thenReturn(user);

            // when & then
            graphQlTest.documentName("queries/user/getCurrentUser")
                    .execute()
                    .path("getCurrentUser")
                    .entity(User.class)
                    .satisfies(user1 -> {
                                assertThat(user1.getFirstname()).isEqualTo(user.getFirstname());
                                assertThat(user1.getLastname()).isEqualTo(user.getLastname());
                            }
                    );

            verify(service).getCurrentUser();
        }
    }
}