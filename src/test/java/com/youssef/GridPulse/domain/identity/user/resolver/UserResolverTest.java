package com.youssef.GridPulse.domain.identity.user.resolver;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.dto.UpdateUserInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.dao.DataAccessException;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@GraphQlTest(UserResolver.class)
@EnableMethodSecurity(prePostEnabled = true)
@AutoConfigureGraphQlTester
class UserResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private UserService userService;

    // Inner class to enable method security - Alternative to using @EnableMethodSecurity(prePostEnabled = true) on main test class
//    @TestConfiguration
//    @EnableMethodSecurity(prePostEnabled = true)
//    static class TestConfig {
//    }

    // Test data
    UUID testUserId = UUID.randomUUID();
    UUID testUserId2 = UUID.randomUUID();
    private User testUser1;
    private User testUser2;
    private UserHistory testUserHistory1;
    private UserHistory testUserHistory2;

    // Test execution tracking
    private static Instant suiteStartTime;
    private static int testCounter = 1;

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        System.out.println("\n⭐ UserResolver Test Execution Started");
        System.out.println("⏰ Start Time: " + suiteStartTime);
        System.out.println("-".repeat(50));
    }

    @AfterAll
    static void endTestExecution() {
        Instant suiteEndTime = Instant.now();
        long duration = java.time.Duration.between(suiteStartTime, suiteEndTime).toMillis();

        System.out.println("-".repeat(50));
        System.out.println("🏁 UserResolver Test Execution Completed");
        System.out.println("⏰ End Time: " + suiteEndTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(50));
    }

    @BeforeEach
    void setUp() {
        System.out.println("📋 Test " + testCounter + " - Setting up...");

        testUser1 = User.builder()
                .id(testUserId)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encoded password")
                .role(Role.USER)
                .enabled(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .source(Source.APP)
                .build();

        testUser2 = User.builder()
                .id(testUserId2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .role(Role.ADMIN)
                .enabled(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .source(Source.APP)
                .build();

        testUserHistory1 = UserHistory.builder()
                .id(UUID.randomUUID())
                .originalId(testUserId)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encoded password")
                .createdBy("SYSTEM")
                .role("ADMIN")
                .enabled(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .source(Source.APP)
                .build();

        testUserHistory2 = UserHistory.builder()
                .id(UUID.randomUUID())
                .originalId(testUserId2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .role("USER")
                .createdBy(testUserId.toString())
                .enabled(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .source(Source.APP)
                .build();
    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Test " + testCounter + " - Completed");
        reset(userService); // Reset mock between tests to avoid interference between tests when running class
        testCounter += 1;
    }


    @Nested
    @DisplayName("getAllUsers Tests")
    class GetAllUsersTests {

        @Test
        @DisplayName("getAllUsers - Should return all users with correct field values")
        @WithMockUser(roles = "ADMIN")
        void getAllUsers_ShouldReturnAllUsersWithCorrectFields() {
            // given
            when(userService.getAllUsers()).thenReturn(List.of(testUser1, testUser2));

            // when & then
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .path("getAllUsers")
                    .entityList(User.class)
                    .hasSize(2)
                    .satisfies(users -> {
                        // Find each user by ID and verify their fields
                        User user1 = users.stream()
                                .filter(u -> u.getId().equals(testUser1.getId()))
                                .findFirst()
                                .orElseThrow();

                        User user2 = users.stream()
                                .filter(u -> u.getId().equals(testUser2.getId()))
                                .findFirst()
                                .orElseThrow();

                        // Verify all fields for user1
                        assertThat(user1.getFirstname()).isEqualTo(testUser1.getFirstname());
                        assertThat(user1.getLastname()).isEqualTo(testUser1.getLastname());
                        assertThat(user1.getEmail()).isEqualTo(testUser1.getEmail());
                        assertThat(user1.getRole()).isEqualTo(testUser1.getRole());
                        assertThat(user1.isEnabled()).isEqualTo(testUser1.isEnabled());

                        // Verify all fields for user2
                        assertThat(user2.getFirstname()).isEqualTo(testUser2.getFirstname());
                        assertThat(user2.getLastname()).isEqualTo(testUser2.getLastname());
                        assertThat(user2.getEmail()).isEqualTo(testUser2.getEmail());
                        assertThat(user2.getRole()).isEqualTo(testUser2.getRole());
                        assertThat(user2.isEnabled()).isEqualTo(testUser2.isEnabled());
                    });

            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("getAllUsers - Should return empty list when no users exist")
        @WithMockUser(roles = "ADMIN")
        void getAllUsers_WhenNoUsers_ShouldReturnEmptyList() {
            // given
            when(userService.getAllUsers()).thenReturn(Collections.emptyList());

            // when & then
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .path("getAllUsers")
                    .entityList(User.class)
                    .hasSize(0);

            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("getAllUsers - Should return correct field mapping")
        @WithMockUser(roles = "ADMIN")
        void getAllUsers_ShouldReturnCorrectFieldMapping() {
            // given
            when(userService.getAllUsers()).thenReturn(List.of(testUser1));
            // when & then - Test specific field mapping
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .path("getAllUsers")
                    .entityList(User.class)
                    .satisfies(users -> {
                        User user = users.get(0);
                        assertThat(user.getId()).isEqualTo(testUserId);
                        assertThat(user.getFirstname()).isEqualTo("John");
                        assertThat(user.getLastname()).isEqualTo("Doe");
                        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
                        assertThat(user.getRole()).isEqualTo(Role.USER);
                        assertThat(user.isEnabled()).isTrue();
                        assertThat(user.getSource()).isEqualTo("APP");
                    });

            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("getAllUsers - Should return data when ADMIN accesses")
        @WithMockUser(roles = "ADMIN")
        void getAllUsers_WhenAdminRole_ShouldReturnUsers() {
            // given
            when(userService.getAllUsers()).thenReturn(List.of(testUser1, testUser2));

            // when & then
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .path("getAllUsers")
                    .entityList(User.class)
                    .hasSize(2);

            // Verify the service WAS called for admin
            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("getAllUsers - Should fail USER access")
        @WithMockUser(roles = "USER")
        void getAllUsers_WhenUserRole_ShouldFail() {
            // when & then
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            // Verify the service was NEVER called for admin
            verify(userService, never()).getAllUsers();
        }

        @Test
        @DisplayName("getAllUsers - Should handle service exceptions gracefully")
        void getAllUsers_ReturnGraphQLError() {
            // given - Simulate actual database exception
            when(userService.getAllUsers()).thenThrow(
                    new DataAccessException("Database connection failed") {
                    }
            );

            // when & then
            graphQlTester.documentName("queries/user/getAllUsers")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR for ");
                    });

            verify(userService, never()).getAllUsers();
        }

    }

    @Nested
    @DisplayName("getUserById Tests")
    @WithMockUser(roles = "ADMIN")
    class GetUserByIdTests {

        @Test
        @DisplayName("getUserById - Should return user with correct field values")
        void getUserById_Success() {
            // GIVEN
            when(userService.getUserById(testUserId)).thenReturn(Optional.ofNullable(testUser1));

            // WHEN & THEN
            graphQlTester.documentName("queries/user/getUserById")
                    .variable("id", testUserId)
                    .execute()
                    .path("getUserById")
                    .entity(User.class)
                    .satisfies(user -> {
                        assertThat(user.getId()).isEqualTo(testUserId);
                        assertThat(user.getFirstname()).isEqualTo("John");
                        assertThat(user.getLastname()).isEqualTo("Doe");
                        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
                        assertThat(user.getRole()).isEqualTo(Role.USER);
                        assertThat(user.isEnabled()).isTrue();
                        assertThat(user.getSource()).isEqualTo("APP");
                    });

            verify(userService).getUserById(testUserId);
        }

        @Test
        @WithMockUser(roles = "USER")
        void getUserById_WhenUserRole_ShouldFail() {

            // when & then
            graphQlTester.documentName("queries/user/getUserById")
                    .variable("id", testUserId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).getUserById(any(UUID.class));

        }

        @Test
        @WithAnonymousUser
        void getUserById_WhenAnonymousRole_ShouldFail() {
            // Debug
//            System.out.println("Response: " + response);
//            System.out.println("Errors: " + response.errors().toString());
//
//            // Check current authentication
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println("Authenticated user: " + auth.getName());
//            System.out.println("User roles: " + auth.getAuthorities());
            // when & then
            graphQlTester.documentName("queries/user/getUserById")
                    .variable("id", testUserId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
            verify(userService, never()).getUserById(any(UUID.class));
        }

        @Test
        @WithAnonymousUser
        void getUserById_WhenException_ShouldFail() {
            // GIVEN
            when(userService.getUserById(testUserId)).thenThrow(
                    new DataAccessException("Database connection failed") {
                    }
            );

            // WHEN & THEN
            graphQlTester.documentName("queries/user/getUserById")
                    .variable("id", testUserId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR for ");
                    });

            verify(userService, never()).getUserById(testUserId);
        }
    }

    @Nested
    @DisplayName("GetAllUsersActivityHistory Tests")
    @WithMockUser(roles = "ADMIN")
    class GetAllUsersActivityHistoryTests {


        @Test
        @DisplayName("getAllUsersActivityHistory - Should return all user activity history records")
        void getUsersActivityHistory() {
            // given
            when(userService.getUsersActivityHistory()).thenReturn(List.of(testUserHistory1, testUserHistory2));

            // when & then
            graphQlTester.documentName("queries/user/getUsersActivityHistory")
                    .execute()
                    .path("getUsersActivityHistory")
                    .entityList(UserHistory.class)
                    .satisfies(userHistories -> {
                        UserHistory userHistory = userHistories.get(0);
                        assertThat(userHistory.getOriginalId()).isEqualTo(testUserId);
                        assertThat(userHistory.getFirstname()).isEqualTo("John");
                        assertThat(userHistory.getLastname()).isEqualTo("Doe");
                        assertThat(userHistory.getEmail()).isEqualTo("john.doe@example.com");
                        assertThat(userHistory.getRole()).isEqualTo(Role.ADMIN.name());
                        assertThat(userHistory.isEnabled()).isTrue();
                        assertThat(userHistory.getSource()).isEqualTo("APP");
                    });

            // Verify the service was called
            verify(userService).getUsersActivityHistory();
        }

        @Test
        void getUsersActivityHistory_Fail() {
            // given
            when(userService.getUsersActivityHistory()).thenThrow(
                    new DataAccessException("Database connection failed") {
                    }
            );

            // when & then
            graphQlTester.documentName("queries/user/getUsersActivityHistory")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("INTERNAL_ERROR for ");
                    });

            // Verify the service was called
            verify(userService).getUsersActivityHistory();
        }

        @Test
        @WithMockUser(roles = "USER", username = "youssef.ammari.795@gmail.com")
        @DisplayName("getUsersActivityHistory - Should deny access when USER tries to access admin endpoint")
        void getUsersActivityHistory_Unauthorized() {
            // Debug
            /*
                var response = graphQlTester.documentName("queries/user/getUsersActivityHistory")
                        .execute();

                System.out.println("Response: " + response);
                System.out.println("Errors: " + response.errors());

                // Check current authentication
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("Authenticated user: " + auth.getName());
                System.out.println("User roles: " + auth.getAuthorities());
            */
            // when & then - Security should block before reaching service
            graphQlTester.documentName("queries/user/getUsersActivityHistory")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        // Your custom AuthenticationEntryPoint returns this message
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            // Verify the service was NEVER called due to security block
            verify(userService, never()).getUsersActivityHistory();
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class UpdateUserTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanUpdateAnyUsers_firstname() {
            UUID anyUserId = UUID.randomUUID();
            User updatedUser = User.builder()
                    .email("john@doe.com")
                    .firstname("NewName")
                    .lastname("doe")
                    .role(Role.USER)
                    .enabled(true)
                    .id(anyUserId)
                    .build();
            when(userService.updateUser(any(), any())).thenReturn(updatedUser);

            graphQlTester.documentName("mutations/user/updateUser")
                    .variable("id", anyUserId.toString())
                    .variable("input", Map.of("firstname", "NewName"))
                    .execute()
                    .path("updateUser.id")
                    .entity(UUID.class)
                    .isEqualTo(anyUserId)
                    .path("updateUser.firstname")
                    .entity(String.class)
                    .isEqualTo("NewName");

            verify(userService).updateUser(eq(anyUserId), any(UpdateUserInput.class));
        }

        @Test
        @WithMockUser(username = "youssef.ammari.795@gmail.com", roles = "USER")
        void userCanUpdateOwnAccount() {
            // Test self-access
            // Given
            UUID ownUserId = UUID.randomUUID();
            User user = User.builder()
                    .id(ownUserId)
                    .firstname("Youssef")
                    .lastname("Ammari")
                    .role(Role.USER)
                    .email("youssef.ammari.795@gmail.com")
                    .password("password")
                    .build();
            User updatedUser = User.builder()
                    .id(ownUserId)
                    .firstname("Joseph")
                    .lastname("Ammari")
                    .role(Role.USER)
                    .email("youssef.ammari.795@gmail.com")
                    .password("password")
                    .build();

            Authentication authentication = new TestingAuthenticationToken(
                    user, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            when(userService.updateUser(eq(ownUserId), any(UpdateUserInput.class))).thenReturn(updatedUser);
            // when & then
            graphQlTester.documentName("mutations/user/updateUser")
                    .variable("id", user.getId()) //
                    .variable("input", Map.of("firstname", "Joseph"))
                    .execute()
                    .path("updateUser.firstname")
                    .entity(String.class)
                    .isEqualTo("Joseph");

            verify(userService).updateUser(eq(ownUserId), any(UpdateUserInput.class));

        }

        @Test
        @WithMockUser(username = "youssef.ammari.795@gmail.com", roles = "USER")
        void userCannotUpdateOtherAccount() {
            // Test unauthorized access
            UUID differentUserId = UUID.randomUUID(); // Different from principal.id

            graphQlTester.documentName("mutations/user/updateUser")
                    .variable("id", differentUserId.toString())
                    .variable("input", Map.of("firstname", "NewName"))
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).updateUser(any(UUID.class), any());
        }

        @Test
        @WithAnonymousUser
        void unauthenticatedCannotUpdate() {
            // Test no authentication
            UUID differentUserId = UUID.randomUUID(); // Different from principal.id

            graphQlTester.documentName("mutations/user/updateUser")
                    .variable("id", differentUserId.toString())
                    .variable("input", Map.of("firstname", "NewName"))
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).updateUser(any(UUID.class), any());
        }
    }

    @Nested
    @DisplayName("Delete Tests")
    class DeleteUserTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canDeleteUser() {
            when(userService.deleteUserById(any(UUID.class))).thenReturn(true);
            graphQlTester.documentName("mutations/user/deleteUserById")
                    .variable("id", testUserId.toString())
                    .execute()
                    .path("deleteUser")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(userService).deleteUserById(testUserId);
        }

        // Negative tests - unauthorized access
        @Test
        @WithMockUser(roles = "USER")
        void userCannotDeleteUser() {
            graphQlTester.documentName("mutations/user/deleteUserById")
                    .variable("id", testUserId.toString())
                    .execute()
                    .errors()
                    .satisfy(error -> {
                        assertThat(error).hasSize(1);
                        assertThat(error.get(0).getMessage()).contains("INTERNAL_ERROR");

                    });

            verify(userService, never()).deleteUserById(any(UUID.class));
        }

        @Test
        void anonymousCannotDeleteUser() {
            SecurityContextHolder.clearContext();

            graphQlTester.documentName("mutations/user/deleteUserById")
                    .variable("id", testUserId.toString())
                    .execute()
                    .errors()
                    .satisfy(error -> error.get(0).getMessage().contains("INTERNAL_ERROR"));

            verify(userService, never()).deleteUserById(any(UUID.class));
        }

    }

    @Nested
    @DisplayName("MarkUserHistorySynced Tests")
    class MarkUserHistorySyncedTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canMarkUserHistorySynced() {
            when(userService.markHistoryRecordAsSynced(testUserHistory1.getId())).thenReturn(true);

            graphQlTester.documentName("mutations/user/markUserHistorySynced")
                    .variable("id", testUserHistory1.getId())
                    .execute()
                    .path("markUserHistorySynced")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(userService).markHistoryRecordAsSynced(testUserHistory1.getId());
        }

        @Test
        @WithMockUser
        void cannotMarkUserHistorySynced_User() {
            graphQlTester.documentName("mutations/user/markUserHistorySynced")
                    .variable("id", testUserHistory1.getId())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).markHistoryRecordAsSynced(any(UUID.class));
        }

        @Test
        @WithAnonymousUser
        void cannotMarkUserHistorySynced_Unauthenticated() {
            SecurityContextHolder.clearContext();

            graphQlTester.documentName("mutations/user/markUserHistorySynced")
                    .variable("id", testUserHistory1.getId())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).markHistoryRecordAsSynced(any(UUID.class));
        }
    }

    @Nested
    @DisplayName("ToggleUserEnableStatus Tests")
    class ToggleUserEnableStatus {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canToggleUserEnableStatus() {
            when(userService.toggleUserEnableStatus(testUserId)).thenReturn(true);

            graphQlTester.documentName("mutations/user/toggleUserEnableStatus")
                    .variable("id", testUserId)
                    .execute()
                    .path("toggleUserEnableStatus")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(userService).toggleUserEnableStatus(testUserId);
        }

        @Test
        @WithMockUser(roles = "USER")
        void cannotToggleUserEnableStatus_User() {
            graphQlTester.documentName("mutations/user/toggleUserEnableStatus")
                    .variable("id", testUserId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).toggleUserEnableStatus(any(UUID.class));
        }

        @Test
        @WithAnonymousUser
        void cannotToggleUserEnableStatus_Unauthenticated() {
            SecurityContextHolder.clearContext();

            graphQlTester.documentName("mutations/user/toggleUserEnableStatus")
                    .variable("id", testUserId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });

            verify(userService, never()).toggleUserEnableStatus(any(UUID.class));
        }
    }

}