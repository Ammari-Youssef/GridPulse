package com.youssef.GridPulse.domain.base;

import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled("Base test class - extend with concrete implementations")
public abstract class BaseResolverTest<R, E extends BaseEntity, H extends BaseHistoryEntity, ID extends UUID, INPUT, S extends BaseService<E, H, ID, INPUT>> {

    @Autowired
    protected GraphQlTester graphQlTester;

    protected S service;

    // Test data
    protected ID testEntityId;
    protected ID testHistoryId;
    protected ID testHistoryId2;
    protected ID testEntityId2;

    protected E testEntity;
    protected E testEntity2;
    protected H testHistory;
    protected H testHistory2;
    protected Map<String, Object> inputMap;

    // Tracking test
    private static Instant suiteStartTime;
    private static int testCounter = 1;

    public BaseResolverTest(S service) {
        this.service = service;
    }

    protected abstract R getResolver();

    protected abstract E createTestEntity(ID id);

    protected abstract H createTestHistoryEntity(ID originalId);

    protected abstract INPUT createTestInput();

    protected abstract ID createTestId();

    protected abstract Class<E> getEntityClass();

    protected abstract Class<H> getHistoryClass();

    protected abstract void assertions(E entity);

    protected abstract void assertionsHistory(H history);

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        TestLogger.logSuiteStart(BaseResolverTest.class);
    }

    @AfterAll
    static void endTestExecution() {
        TestLogger.logSuiteEnd(BaseResolverTest.class, suiteStartTime);
    }

    @BeforeEach
    void setUp() {
        TestLogger.logTestStart(testCounter);
        testEntityId = createTestId();
        testHistoryId = createTestId();
        testEntityId2 = createTestId();
        testHistoryId2 = createTestId();

        testEntity = createTestEntity(testEntityId);
        testEntity2 = createTestEntity(testEntityId2);
        testHistory = createTestHistoryEntity(testEntityId);
        testHistory2 = createTestHistoryEntity(testEntityId2);

        INPUT testInput = createTestInput();
        inputMap = convertInputToMap(testInput);
    }

    @AfterEach
    void tearDown() {
        reset(service);
        TestLogger.logTestEnd(testCounter);
        testCounter++;
    }

    protected abstract Map<String, Object> convertInputToMap(INPUT input);

    // ==================== NESTED TEST CLASSES ====================

    @Nested
    @DisplayName("Create Tests")
    class CreateTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanCreateEntity() {
            when(service.create(any())).thenReturn(testEntity);

            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/create")
                    .variable("input", inputMap)
                    .execute()
                    .path("create" + getEntityClass().getSimpleName())
                    .entity(getEntityClass())
                    .satisfies(entity -> {
                        assertThat(entity).isNotNull();
                        assertions(entity);
                        // Entity-specific assertions can be added in concrete classes
                    });

            verify(service).create(any());
        }

        @Test
        @WithMockUser(roles = "USER")
        void userCannotCreateEntity() {
            testCreateEntity_Unauthorized("USER");
        }

        @Test
        @WithAnonymousUser
        void anonymousCannotCreateEntity() {
            testCreateEntity_Unauthorized("ANONYMOUS");
        }
    }

    @Nested
    @DisplayName("Read Tests")
    class ReadTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanGetAllEntities() {
            testGetAll_AdminSuccess();
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanGetEntityById() {
            testGetById_Success(testEntityId, "ADMIN");
        }

        @Test
        @WithMockUser(roles = "USER")
        void userCanGetEntityById() {
            testGetById_Success(testEntityId, "USER");
        }

        @Test
        @WithAnonymousUser
        void anonymousCannotGetEntityById() {
            graphQlTester.documentName("queries/" + getEntityClass().getSimpleName() + "/getById")
                    .variable("id", testEntityId)
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }
    }

    @Nested
    @DisplayName("Update Tests")
    class UpdateTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanUpdateEntity() {
            when(service.update(any(), any())).thenReturn(testEntity);

            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/update")
                    .variable("id", testEntityId.toString())
                    .variable("input", inputMap)
                    .execute()
                    .path("update" + getEntityClass().getSimpleName())
                    .entity(getEntityClass())
                    .satisfies(entity -> {
                        assertThat(entity).isNotNull();
                        assertThat(entity.getId()).isEqualTo(testEntityId);
                    });

            // Debug
            System.out.println("path: update" + getEntityClass().getSimpleName() + ".id");

            verify(service).update(any(), any());
        }

        @Test
        @WithMockUser(roles = "USER")
        void userCannotUpdateEntity() {
            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName() + "/update")
                    .variable("id", testEntityId.toString())
                    .variable("input", inputMap)
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }
    }

    @Nested
    @DisplayName("Delete Tests")
    class DeleteTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanDeleteEntity() {
            testDelete_AdminSuccess(testEntityId);
        }

        @Test
        @WithMockUser(roles = "USER")
        void userCannotDeleteEntity() {
            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName() + "/deleteById")
                    .variable("id", testEntityId.toString())
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }
    }

    @Nested
    @DisplayName("History Tests")
    class HistoryTests {
        //       ================== GetHistoryById ==================
        @Test
        @WithMockUser(roles = "ADMIN")
        void adminCanGetHistoryById() {
            testGetHistoryById_AdminSuccess(testHistoryId);
        }

        @Test
        @WithMockUser(roles = "USER")
        void userCannotGetHistoryById() {
            graphQlTester.documentName("queries/" + getEntityClass().getSimpleName().toLowerCase() + "/getHistoryById")
                    .variable("historyId", testHistoryId)
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }

        @Test
        @WithAnonymousUser
        void anonymousCannotGetHistoryById() {
            graphQlTester.documentName("queries/" + getEntityClass().getSimpleName().toLowerCase() + "/getHistoryById")
                    .variable("historyId", testHistoryId)
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }

        //       ================== GetHistoryListByOriginalId ==================
        @Nested
        @DisplayName("HistoryListByOriginalIdTests")
        class GetHistoryByOriginalIdTests {

            @Test
            @DisplayName("findHistoryByOriginalId - Should return all entity activity history records")
            @WithMockUser(roles = "ADMIN")
            void findHistoryByOriginalId() {
                // given
                when(service.findHistoryByOriginalId(testEntityId)).thenReturn(List.of(testHistory));

                // when & then
                graphQlTester.documentName("queries/" +getEntityClass().getSimpleName().toLowerCase() + "/getHistoryByOriginalId")
                        .variable("originalId", testEntityId)
                        .execute()
                        .path("get" + getEntityClass().getSimpleName() + "History")
                        .entityList(getHistoryClass())
                        .satisfies(histories -> {
                            H history = histories.get(0);
                            assertThat(history.getOriginalId()).isEqualTo(testEntityId);

                            assertionsHistory(history);
                        });

                // Verify the service was called
                verify(service).findHistoryByOriginalId(testEntityId);
            }

            @Test
            @WithMockUser
            void findHistoryByOriginalId_UserFail() {
                graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getHistoryByOriginalId")
                        .variable("originalId", testEntityId)
                        .execute()
                        .errors()
                        .satisfy(errors -> {
                            assertThat(errors).hasSize(1);
                            assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                        });

                verify(service, never()).findHistoryByOriginalId(testEntityId);
            }

            @Test
            @WithAnonymousUser
            void findHistoryByOriginalId_Fail() {
                // given - Use a concrete exception
//                when(service.findHistoryByOriginalId(testEntityId))
//                        .thenThrow(new BadCredentialsException("Authentication failed"));  // ✅ Concrete exception

                // when & then
                graphQlTester.documentName("queries/" + Inverter.class.getSimpleName() + "/getHistoryByOriginalId")
                        .variable("originalId", testEntityId)
                        .execute()
                        .errors()
                        .satisfy(errors -> {
                            assertThat(errors).isNotNull();
                            assertThat(errors.size()).isEqualTo(1);
                            assertThat(errors.get(0).getMessage())
                                    .contains("INTERNAL_ERROR for ");
                        });

                verify(service, never()).findHistoryByOriginalId(testEntityId);
            }
        }


    }

    @Nested
    @DisplayName("MarkHistorySyncedTests")
    class MarkHistorySyncedTests {

        @Test
        @WithMockUser(roles = "ADMIN")
        void canMarkHistoryAsSynced() {
            when(service.markHistoryRecordAsSynced(any())).thenReturn(true);

            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testHistoryId.toString())
                    .execute()
                    .path("mark" + getEntityClass().getSimpleName() + "HistorySynced")
                    .entity(Boolean.class)
                    .isEqualTo(true);

            verify(service).markHistoryRecordAsSynced(any());
        }

        @Test
        @WithMockUser
        void cannotMarkHistorySynced_AuthUser() {
            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testHistoryId.toString())
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }

        @Test
        @WithAnonymousUser
        void cannotMarkEntityHistorySynced_Unauthenticated() {
            graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/markHistorySynced")
                    .variable("id", testHistoryId.toString())
                    .execute()
                    .errors()
                    .satisfy(err -> {
                        assertThat(err).hasSize(1);
                        assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                    });
        }
    }

    // ==================== PROTECTED HELPER METHODS ====================

    protected void testCreateEntity_Unauthorized(String role) {
        when(service.create(any())).thenThrow(HttpClientErrorException.Unauthorized.class);
        graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/create")
                .variable("input", inputMap)
                .execute()
                .errors()
                .satisfy(err -> {
                    assertThat(err).hasSize(1);
                    assertThat(err.get(0).getMessage()).contains("INTERNAL_ERROR");
                });

        verify(service, never()).create(any());
    }

    protected void testUpdateEntity_AdminSuccess(ID id, E e) {
        when(service.update(any(), any())).thenReturn(testEntity);

        graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName().toLowerCase() + "/update")
                .variable("id", id.toString())
                .variable("input", inputMap)
                .execute()
                .path("update" + getEntityClass().getSimpleName())
                .entity(getEntityClass())
                .satisfies(entity -> {
                    assertThat(entity).isNotNull();
                    assertThat(entity.getId()).isEqualTo(id);
                    assertions(e);
                });

        verify(service).update(any(), any());
    }

    protected void testGetAll_AdminSuccess() {
        when(service.getAll()).thenReturn(List.of(testEntity, testEntity2));

        graphQlTester.documentName("queries/" + getEntityClass().getSimpleName().toLowerCase() + "/getAll")
                .execute()
                .path("getAll" + getEntityClass().getSimpleName() + "s")
                .entityList(getEntityClass())
                .hasSize(2);

        verify(service).getAll();
    }

    protected void testGetById_Success(ID id, String role) {
        when(service.getEntityById(id)).thenReturn(testEntity);

        graphQlTester.documentName("queries/" + getEntityClass().getSimpleName().toLowerCase() + "/getById")
                .variable("id", id)
                .execute()
                .path("get" + getEntityClass().getSimpleName() + "ById")
                .entity(getEntityClass())
                .satisfies(entity -> {
                    assertThat(entity).isNotNull();
                    assertions(entity);
                });

        verify(service).getEntityById(id);
    }

    protected void testGetHistoryById_AdminSuccess(ID historyId) {
        when(service.findHistoryById(historyId)).thenReturn(testHistory);

        graphQlTester.documentName("queries/" + getEntityClass().getSimpleName().toLowerCase() + "/getHistoryById")
                .variable("historyId", historyId)
                .execute()
                .path("get" + getEntityClass().getSimpleName() + "HistoryById")
                .entity(getHistoryClass())
                .satisfies(history -> assertThat(history).isNotNull());
    }

    protected void testDelete_AdminSuccess(ID id) {
        when(service.delete(id)).thenReturn(true);

        graphQlTester.documentName("mutations/" + getEntityClass().getSimpleName() + "/deleteById")
                .variable("id", id.toString())
                .execute()
                .path("delete" + getEntityClass().getSimpleName())
                .entity(Boolean.class)
                .isEqualTo(true);

        verify(service).delete(id);
    }


}