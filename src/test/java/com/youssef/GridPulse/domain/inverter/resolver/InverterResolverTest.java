package com.youssef.GridPulse.domain.inverter.resolver;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.configuration.graphql.GraphQLConfig;
import com.youssef.GridPulse.domain.base.BaseHistoryRepositoryTest;
import com.youssef.GridPulse.utils.TestLogger;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.resolver.InverterResolver;
import com.youssef.GridPulse.domain.inverter.inverter.service.InverterService;
import com.youssef.GridPulse.utils.TestSuiteUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

/**
 * Test class for InverterResolver using regular GraphQL testing.
 * {@link InverterResolverTests} will use {@link com.youssef.GridPulse.domain.base.BaseResolverTest} for more generic tests.
 */
@GraphQlTest(InverterResolver.class)
@EnableMethodSecurity
@AutoConfigureGraphQlTester
@Import(GraphQLConfig.class)
class InverterResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private InverterService service;

    // Test data
    UUID testInverterId;
    UUID testInverterHistoryId1;

    private Inverter testInverter1;
    private Inverter testInverter2;
    private InverterHistory testInverterHistory1;
    private InverterHistory testInverterHistory2;
    Map<String, Object> inputMap;


    // Tracking test
    private static Instant suiteStartTime;
    private static int testCounter = 1;

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        TestLogger.logSuiteStart(BaseHistoryRepositoryTest.class);
    }

    @AfterAll
    static void endTestExecution() {
        TestLogger.logSuiteEnd(BaseHistoryRepositoryTest.class, suiteStartTime);
    }

    @BeforeEach
    void setUp() {
        TestLogger.logTestStart(testCounter);
        testInverterId = TestSuiteUtils.TEST_INVERTER_ID;
        testInverterHistoryId1 = TestSuiteUtils.TEST_INVERTER_HISTORY_ID;

        testInverter1 = TestSuiteUtils.createTestInverterA();


        testInverter2 = TestSuiteUtils.createTestInverterB();

        testInverterHistory1 = TestSuiteUtils.createTestInverterHistoryA();

        testInverterHistory2 = TestSuiteUtils.createTestInverterHistoryB();

        InverterInput testInput = TestSuiteUtils.createTestInverterInput();

        inputMap = Map.of(
                "name", testInput.name(),
                "model", testInput.model(),
                "version", testInput.version(),
                "manufacturer", testInput.manufacturer()
        );
    }

    @AfterEach
    void tearDown() {
        reset(service); // Reset mock between tests to avoid interference between tests when running class
        TestLogger.logTestEnd(testCounter);
        testCounter++;
    }

    @Nested
    @DisplayName("CreateTests")
    class CreateTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canCreateEntity() {

            when(service.create(any(InverterInput.class))).thenReturn(testInverter1);

            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/create")
                    .variable("input", inputMap)
                    .execute()
                    .path("create" + Inverter.class.getSimpleName())
                    .entity(Inverter.class)
                    .satisfies(entity -> {
                        assertThat(entity.getId()).isEqualTo(testInverterId);
                        assertThat(entity.getName()).isEqualTo(testInverter1.getName());
                        assertThat(entity.getManufacturer()).isEqualTo(testInverter1.getManufacturer());
                        assertThat(entity.getModel()).isEqualTo(testInverter1.getModel());
                        assertThat(entity.getVersion()).isEqualTo(testInverter1.getVersion());
                    });

        }

        @Test
        @WithMockUser
        void cannotCreateInverter() {
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/create")
                    .variable("input", inputMap)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            // Verify the service was NEVER called
            verify(service, never()).create(any());
        }

        @Test
        @WithAnonymousUser
        void cannotCreateInverter_Anonymous() {
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/create")
                    .variable("input", inputMap)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            // Verify the service was NEVER called
            verify(service, never()).create(any());
        }
    }

    @Nested
    @DisplayName("getAllTests")
    class GetAllTests {

        @Test
        @DisplayName("Should return all Inverters with correct field values")
        @WithMockUser(roles = "ADMIN")
        void getAll_ShouldReturnAllWithCorrectFields() {
            // given
            when(service.getAll()).thenReturn(List.of(testInverter1, testInverter2));

            // when & then
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .path("getAll" + Inverter.class.getSimpleName() + "s")
                    .entityList(Inverter.class)
                    .hasSize(2)
                    .satisfies(Inverters -> {
                        // Find each Inverter by ID and verify their fields
                        Inverter Inverter1 = Inverters.stream()
                                .filter(u -> u.getId().equals(testInverter1.getId()))
                                .findFirst()
                                .orElseThrow();

                        Inverter Inverter2 = Inverters.stream()
                                .filter(u -> u.getId().equals(testInverter2.getId()))
                                .findFirst()
                                .orElseThrow();

                        // Verify all fields for Inverter1
                        assertThat(Inverter1.getManufacturer()).isEqualTo(testInverter1.getManufacturer());
                        assertThat(Inverter1.getModel()).isEqualTo(testInverter1.getModel());
                        assertThat(Inverter1.getName()).isEqualTo(testInverter1.getName());
                        assertThat(Inverter1.getVersion()).isEqualTo(testInverter1.getVersion());

                        // Verify all fields for Inverter2
                        assertThat(Inverter2.getManufacturer()).isEqualTo(testInverter2.getManufacturer());
                        assertThat(Inverter2.getModel()).isEqualTo(testInverter2.getModel());
                        assertThat(Inverter2.getName()).isEqualTo(testInverter2.getName());
                        assertThat(Inverter2.getVersion()).isEqualTo(testInverter2.getVersion());
                    });

            verify(service).getAll();
        }

        @Test
        @DisplayName("Should return empty list when no Inverters exist")
        @WithMockUser(roles = "ADMIN")
        void getAll_WhenNoInverters_ShouldReturnEmptyList() {
            // given
            when(service.getAll()).thenReturn(Collections.emptyList());

            // when & then
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .path("getAll" + Inverter.class.getSimpleName() + "s")
                    .entityList(Inverter.class)
                    .hasSize(0);

            verify(service).getAll();
        }

        @Test
        @DisplayName("Should return correct field mapping")
        @WithMockUser(roles = "ADMIN")
        void getAll_ShouldReturnCorrectFieldMapping() {
            // given
            when(service.getAll()).thenReturn(List.of(testInverter1));
            // when & then - Test specific field mapping
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .path("getAll" + Inverter.class.getSimpleName() + "s")
                    .entityList(Inverter.class)
                    .satisfies(Inverters -> {
                        Inverter Inverter = Inverters.get(0);
                        assertThat(Inverter.getId()).isEqualTo(testInverterId);
                        assertThat(Inverter.getName()).isEqualTo("Test Inverter");
                        assertThat(Inverter.getManufacturer()).isEqualTo("Test Manufacturer");
                        assertThat(Inverter.getModel()).isEqualTo("Test Model");
                        assertThat(Inverter.getVersion()).isEqualTo("Test Version");

                    });

            verify(service).getAll();
        }

        @Test
        @DisplayName("Should return data when ADMIN accesses")
        @WithMockUser(roles = "ADMIN")
        void getAll_WhenAdminRole_ShouldReturnInverters() {
            // given
            when(service.getAll()).thenReturn(List.of(testInverter1, testInverter2));

            // when & then
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .path("getAll" + Inverter.class.getSimpleName() + "s")
                    .entityList(Inverter.class)
                    .hasSize(2);

            // Verify the service WAS called for admin
            verify(service).getAll();
        }

        @Test
        @DisplayName("Should fail Inverter access")
        @WithMockUser(roles = "User")
        void getAll_WhenUserRole_ShouldFail() {
            // when & then
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            // Verify the service was NEVER called for admin
            verify(service, never()).getAll();
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("Should handle service exceptions gracefully")
        void getAll_ReturnGraphQLError() {
            // given - Simulate actual database exception
            when(service.getAll()).thenThrow(
                    new DataAccessException("Database connection failed") {}
            );

            // when & then
            graphQlTester.documentName("queries/Inverter/getAll")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("Database access error. Please try again later.");
                    });

            verify(service).getAll();
        }

    }

    @Nested
    @DisplayName("getById Tests")
    @WithMockUser
    class GetByIdTests {

        @Test
        @DisplayName("Should return user with correct field values for admin")
        @WithMockUser(roles = "ADMIN")
        void getEntityById_Success() {
            // GIVEN
            when(service.getEntityById(testInverterId)).thenReturn(testInverter1);

            // WHEN & THEN
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getById")
                    .variable("id", testInverterId)
                    .execute()
                    .path("get" + Inverter.class.getSimpleName() + "ById")
                    .entity(Inverter.class)
                    .satisfies(entity -> {
                        assertThat(entity.getId()).isEqualTo(testInverterId);
                        assertThat(entity.getName()).isEqualTo("Test Inverter");
                        assertThat(entity.getModel()).isEqualTo("Test Model");
                        assertThat(entity.getManufacturer()).isEqualTo("Test Manufacturer");
                        assertThat(entity.getVersion()).isEqualTo("Test Version");
                        assertThat(entity.getSource()).isEqualTo(Source.APP);
                    });

            verify(service).getEntityById(testInverterId);
        }

        @Test
        @DisplayName("Should return user with correct field values")
        void getEntityById_User_Success() {
            // GIVEN
            when(service.getEntityById(testInverterId)).thenReturn(testInverter1);

            // WHEN & THEN
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getById")
                    .variable("id", testInverterId)
                    .execute()
                    .path("get" + Inverter.class.getSimpleName() + "ById")
                    .entity(Inverter.class)
                    .satisfies(entity -> {
                        assertThat(entity.getId()).isEqualTo(testInverterId);
                        assertThat(entity.getName()).isEqualTo("Test Inverter");
                        assertThat(entity.getModel()).isEqualTo("Test Model");
                        assertThat(entity.getManufacturer()).isEqualTo("Test Manufacturer");
                        assertThat(entity.getVersion()).isEqualTo("Test Version");
                        assertThat(entity.getSource()).isEqualTo(Source.APP);
                    });

            verify(service).getEntityById(testInverterId);
        }


        @Test
        @WithAnonymousUser
        void getEntityById_WhenAnonymousRole_ShouldFail() {
            // Debug
//            System.out.println("Response: " + response);
//            System.out.println("Errors: " + response.errors().toString());
//
//            // Check current authentication
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println("Authenticated user: " + auth.getName());
//            System.out.println("User roles: " + auth.getAuthorities());
            // when & then
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getById")
                    .variable("id", testInverterId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });
            verify(service, never()).getEntityById(any(UUID.class));
        }

        @Test
        @WithMockUser
        void getEntityById_WhenException_ShouldFail() {
            // GIVEN
            when(service.getEntityById(any(UUID.class))).thenThrow(
                    new DataAccessException("Database connection failed") {}
            );

            // WHEN & THEN
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getById")
                    .variable("id", UUID.randomUUID())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("Database access error. Please try again later.");
                    });

            verify(service, never()).getEntityById(testInverterId);
        }
    }

    @Nested
    @DisplayName("GetAllInverterHistory Tests")
    @WithMockUser(roles = "ADMIN")
    class GetAllHistoryTests {


        @Test
        @DisplayName("getAllHistory - Should return all entity activity history records")
        void findAllHistory() {
            // given
            when(service.findAllHistory()).thenReturn(List.of(testInverterHistory1, testInverterHistory2));

            // when & then
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getAllHistory")
                    .execute()
                    .path("getAll" + Inverter.class.getSimpleName() + "History")
                    .entityList(InverterHistory.class)
                    .satisfies(histories -> {
                        InverterHistory history = histories.get(0);
                        assertThat(history.getOriginalId()).isEqualTo(testInverterId);
                        assertThat(history.getManufacturer()).isEqualTo(testInverter1.getManufacturer());
                        assertThat(history.getName()).isEqualTo(testInverter1.getName());
                        assertThat(history.getVersion()).isEqualTo(testInverter1.getVersion());
                        assertThat(history.getModel()).isEqualTo(testInverter1.getModel());
                        assertThat(history.getSource()).isEqualTo(Source.APP);
                    });

            // Verify the service was called
            verify(service).findAllHistory();
        }

        @Test
        void findAllHistory_Fail() {
            // given
            when(service.findAllHistory()).thenThrow(
                    new DataAccessException("Database connection failed") {}
            );

            // when & then
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getAllHistory")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("Database access error. Please try again later.");
                    });

            // Verify the service was called
            verify(service).findAllHistory();
        }

        @Test
        @WithMockUser(roles = "USER", username = "youssef.ammari.795@gmail.com")
        @DisplayName("findAllHistory - Should deny access when USER tries to access admin endpoint")
        void findAllHistory_Unauthorized() {
            // when & then - Security should block before reaching service
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getAllHistory")
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        // Your custom AuthenticationEntryPoint returns this message
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            // Verify the service was NEVER called due to security block
            verify(service, never()).findAllHistory();
        }
    }

    @Nested
    @DisplayName("GetInverterHistoryById Tests")
    class GetHistoryByIdTests {

        @Test
        @DisplayName("Should return entity record if Admin")
        @WithMockUser(roles = "ADMIN")
        void can_findHistoryByID() {

            when(service.findHistoryById(testInverterHistoryId1)).thenReturn(testInverterHistory1);
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName().toLowerCase() + "/getHistoryById")
                    .variable("historyId", testInverterHistoryId1)
                    .execute()
                    .path("get" + Inverter.class.getSimpleName() + "HistoryById")
                    .entity(InverterHistory.class)
                    .satisfies(history -> {
                        assertThat(history).isNotNull();
                        assertThat(history.getId()).isEqualTo(testInverterHistoryId1);
                        assertThat(history.getOriginalId()).isEqualTo(testInverterHistory1.getOriginalId());
                        assertThat(history.getManufacturer()).isEqualTo(testInverterHistory1.getManufacturer());
                        assertThat(history.getName()).isEqualTo(testInverterHistory1.getName());
                        assertThat(history.getVersion()).isEqualTo(testInverterHistory1.getVersion());
                        assertThat(history.getModel()).isEqualTo(testInverterHistory1.getModel());
                        assertThat(history.getSource()).isEqualTo(Source.APP);

                    });
        }

        @Test
        @DisplayName("Should fail when regular user")
        @WithMockUser
        void cannot_findHistoryByID_User() {
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName().toLowerCase() + "/getHistoryById")
                    .variable("historyId", testInverterId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).findHistoryById(testInverterId);
        }

        @Test
        @DisplayName("Should fail when anonymous")
        @WithAnonymousUser
        void cannot_findHistoryByID_Anonymous() {
//            SecurityContextHolder.clearContext();

            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName().toLowerCase() + "/getHistoryById")
                    .variable("historyId", testInverterId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).findHistoryById(testInverterId);
        }
    }

    @Nested
    @DisplayName("GetInverterHistoryByOriginalId Tests")
    @WithMockUser(roles = "ADMIN")
    class GetHistoryByOriginalIdTests {
        @Test
        @DisplayName("findHistoryByOriginalId - Should return all entity activity history records")
        void findHistoryByOriginalId() {
            // given
            when(service.findHistoryByOriginalId(testInverterId)).thenReturn(List.of(testInverterHistory1));

            // when & then
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getHistoryByOriginalId")
                    .variable("originalId", testInverterId)
                    .execute()
                    .path("get" + Inverter.class.getSimpleName() + "History")
                    .entityList(InverterHistory.class)
                    .satisfies(histories -> {
                        InverterHistory history = histories.get(0);
                        assertThat(history.getOriginalId()).isEqualTo(testInverterId);
                        assertThat(history.getManufacturer()).isEqualTo(testInverter1.getManufacturer());
                        assertThat(history.getName()).isEqualTo(testInverter1.getName());
                        assertThat(history.getVersion()).isEqualTo(testInverter1.getVersion());
                        assertThat(history.getModel()).isEqualTo(testInverter1.getModel());
                        assertThat(history.getSource()).isEqualTo(Source.APP);
                    });

            // Verify the service was called
            verify(service).findHistoryByOriginalId(testInverterId);
        }

        @Test
        @WithMockUser
        void findHistoryByOriginalId_UserFail() {
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getHistoryByOriginalId")
                    .variable("originalId", testInverterId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).findHistoryByOriginalId(testInverterId);
        }

        @Test
        void findHistoryByOriginalId_Fail() {
            // given
            when(service.findHistoryByOriginalId(testInverterId)).thenThrow(
                    new DataAccessException("Database connection failed") {
                    }
            );

            // when & then
            graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getHistoryByOriginalId")
                    .variable("originalId", testInverterId)
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).isNotNull();
                        assertThat(errors.size()).isEqualTo(1);
                        // GraphQL might wrap the exception message
                        assertThat(errors.get(0).getMessage())
                                .contains("Database access error. Please try again later.");
                    });

            // Verify the service was called
            verify(service).findHistoryByOriginalId(testInverterId);
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class updateTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanUpdateAnyEntity_name() {
            UUID anyEntityId = UUID.randomUUID();
            String fieldToChange = "name";
            String expected = "TestName";

            // Create updated entity with the SAME ID that was passed in
            Inverter updatedEntity = Inverter.builder()
                    .id(anyEntityId)  // ✅ CRITICAL: Use the same ID!
                    .name(expected)
                    .model("Test Model")
                    .version("v2.1")
                    .manufacturer("SolarEdge")
                    .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                    .build();

            when(service.update(eq(anyEntityId), any(InverterInput.class))).thenReturn(updatedEntity);

            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/update")
                    .variable("id", anyEntityId.toString())
                    .variable("input", Map.of(fieldToChange, expected))
                    .execute()
                    .path("updateInverter.id")  // ✅ Use actual operation name, not dynamic
                    .entity(UUID.class)
                    .isEqualTo(anyEntityId)
                    .path("updateInverter." + fieldToChange)
                    .entity(String.class)
                    .isEqualTo(expected);

            verify(service).update(eq(anyEntityId), any(InverterInput.class));
        }

        @Test
        @WithAnonymousUser
        void guestCannotUpdate() {
            // Test no authentication
            UUID differentUserId = UUID.randomUUID(); // Different from principal.id

            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/update")
                    .variable("id", differentUserId.toString())
                    .variable("input", Map.of("name", "NewName"))
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).update(any(UUID.class), any());
        }
    }

    @Nested
    @DisplayName("Delete Tests")
    class DeleteUserTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canDeleteEntity() {
            when(service.delete(any(UUID.class))).thenReturn(true);
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/deleteById")
                    .variable("id", testInverterId.toString())
                    .execute()
                    .path("deleteInverter")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(service).delete(testInverterId);
        }

        // Negative tests - unauthorized access
        @Test
        @WithMockUser(roles = "USER")
        void userCannotDeleteUser() {
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/deleteById")
                    .variable("id", testInverterId.toString())
                    .execute()
                    .errors()
                    .satisfy(error -> {
                        assertThat(error).hasSize(1);
                        assertThat(error.get(0).getMessage()).contains("Forbidden: admin privileges required");

                    });

            verify(service, never()).delete(any(UUID.class));
        }

        @Test
        void anonymousCannotDeleteUser() {
            SecurityContextHolder.clearContext();

            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/deleteById")
                    .variable("id", testInverterId.toString())
                    .execute()
                    .errors()
                    .satisfy(error -> error.get(0).getMessage().contains("INTERNAL_ERROR"));

            verify(service, never()).delete(any(UUID.class));
        }

    }

    @Nested
    @DisplayName("MarkEntityHistorySynced Tests")
    class MarkInverterHistorySyncedTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canMarkInverterHistorySynced() {
            when(service.markHistoryRecordAsSynced(testInverterHistory1.getId())).thenReturn(true);

            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testInverterHistory1.getId())
                    .execute()
                    .path("mark" + Inverter.class.getSimpleName() + "HistorySynced")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(service).markHistoryRecordAsSynced(testInverterHistory1.getId());
        }

        @Test
        @WithMockUser
        void cannotMarkHistorySynced_UnauthorizedUser() {
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testInverterHistory1.getId())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).markHistoryRecordAsSynced(any(UUID.class));
        }

        @Test
        @WithAnonymousUser
        void cannotMarkInverterHistorySynced_Guest() {
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testInverterHistory1.getId())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Forbidden: admin privileges required");
                    });

            verify(service, never()).markHistoryRecordAsSynced(any(UUID.class));
        }

        @Test
        void cannotMarkInverterHistorySynced_Unauthenticated() {

            SecurityContextHolder.clearContext();
            graphQlTester.documentName("mutations/" + Inverter.class.getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testInverterHistory1.getId())
                    .execute()
                    .errors()
                    .satisfy(errors -> {
                        assertThat(errors).hasSize(1);
                        assertThat(errors.get(0).getMessage()).contains("Unauthorized: authentication required");
                    });

            verify(service, never()).markHistoryRecordAsSynced(any(UUID.class));
        }
    }

}
