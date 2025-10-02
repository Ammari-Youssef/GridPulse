package com.youssef.GridPulse.domain.base;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public abstract class BaseHistoryRepositoryTest<H extends BaseHistoryEntity, ID extends UUID> {

    @Autowired
    protected TestEntityManager entityManager;

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
    }

    @AfterEach
    void tearDown() {
        TestLogger.logTestEnd(testCounter);
        testCounter++;
    }

    protected abstract BaseHistoryRepository<H, ID> getRepository();
    protected abstract H createTestHistoryEntity(UUID originalId);
    protected abstract H createTestHistoryEntity();
    protected abstract Class<H> getEntityClass();

    @Nested
    @DisplayName("MarkAsSynced Operations")
    class MarkAsSyncedTests {

        @Test
        @DisplayName("Should update record when ID exists")
        void markAsSynced_WhenIdExists_ShouldUpdateRecord() {
            // Given
            H historyEntity = createTestHistoryEntity();
            historyEntity.setSynced(false);
//            H savedEntity = entityManager.persistFlushFind(historyEntity);
            entityManager.persist(historyEntity);
            entityManager.flush();

            // When
            int updatedCount = getRepository().markAsSynced((ID) historyEntity.getId());

            entityManager.flush();
            entityManager.clear();

            // Then
            assertThat(updatedCount).isEqualTo(1L);
            // Verify the update actually happened
            H updatedEntity = entityManager.find(getEntityClass(), historyEntity.getId());
            assertThat(updatedEntity.isSynced()).isTrue();
        }

        @Test
        @DisplayName("Should return zero when ID does not exist")
        void markAsSynced_WhenIdNotExists_ShouldReturnZero() {
            // When
            int updatedCount = getRepository().markAsSynced((ID) UUID.randomUUID());

            // Then
            assertThat(updatedCount).isZero();
        }

        /**
         * Performance test to ensure the markAsSynced operation completes within 2 seconds.
         * Newer test not in UserHistoryRepoTest
         */
        @Test
        @Timeout(3)
        @DisplayName("Should complete within 3 seconds")
        void markAsSynced_ShouldCompleteQuickly() {
            // Given
            H entity = createTestHistoryEntity();
            entity.setSynced(false);
            H savedEntity = entityManager.persistFlushFind(entity);

            // When & Then - Should not time out
            assertThatCode(() -> getRepository().markAsSynced((ID) savedEntity.getId()))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("FindByOriginalId Operations")
    class FindByOriginalIdTests {

        @Test
        @DisplayName("Should return matching entities when records exist")
        void findByOriginalId_WhenRecordsExist_ShouldReturnMatchingEntities() {
            // Given
            UUID targetOriginalId = UUID.randomUUID();
            UUID otherOriginalId = UUID.randomUUID();

            H history1 = createTestHistoryEntity(targetOriginalId);
            H history2 = createTestHistoryEntity(targetOriginalId);
            H history3 = createTestHistoryEntity(otherOriginalId);

            entityManager.persist(history1);
            entityManager.persist(history2);
            entityManager.persist(history3);

            entityManager.flush();

            // When
            List<H> result = getRepository().findByOriginalId(targetOriginalId);

            // Then
            assertThat(result)
                    .hasSize(2)
                    .extracting(BaseHistoryEntity::getOriginalId)
                    .containsOnly(targetOriginalId);
        }

        @Test
        @DisplayName("Should return empty list when no records exist")
        void findByOriginalId_WhenNoRecordsExist_ShouldReturnEmptyList() {
            // When
            List<H> result = getRepository().findByOriginalId(UUID.randomUUID());

            // Then
            assertThat(result).isEmpty();
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Should return empty list for null originalId")
        void findByOriginalId_WhenOriginalIdIsNull_ShouldReturnEmptyList(UUID nullId) {
            // When
            List<H> result = getRepository().findByOriginalId(nullId);

            // Then
            assertThat(result).isEmpty();
        }

        /**
         * New test not necessary
         */
        @Test
        @Timeout(4)
        @DisplayName("Should complete query within 4 seconds")
        void findByOriginalId_ShouldCompleteQuickly() {
            // Given
            UUID originalId = UUID.randomUUID();
            H entity = createTestHistoryEntity(originalId);
            entityManager.persistFlushFind(entity);

            // When & Then - Should not time out
            assertThatCode(() -> getRepository().findByOriginalId(originalId))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("CRUD Inheritance Tests")
    class CrudInheritanceTests {

        @Test
        @DisplayName("Should support inherited save operation")
        void save_ShouldWork() {
            // Given
            H history = createTestHistoryEntity();

            // When
            H saved = getRepository().save(history);

            // Then
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getOriginalId()).isEqualTo(history.getOriginalId());
        }

        @Test
        @DisplayName("Should support inherited findById operation")
        void findById_ShouldWork() {
            // Given
            H history = createTestHistoryEntity();
            H saved = getRepository().save(history);

            // When
            Optional<H> found = getRepository().findById((ID) saved.getId());

            // Then
            assertThat(found).isPresent();
            assertThat(found.get().getId()).isEqualTo(saved.getId());
        }

        @Test
        @DisplayName("Should support inherited delete operation")
        void delete_ShouldWork() {
            // Given
            H history = createTestHistoryEntity();
            H saved = getRepository().save(history);

            // When
            getRepository().deleteById((ID) saved.getId());

            // Then
            assertThat(getRepository().findById((ID) saved.getId())).isEmpty();
        }

        @Test
        @DisplayName("Should support inherited findAll operation")
        void findAll_ShouldWork() {
            // Given
            H entity1 = createTestHistoryEntity();
            H entity2 = createTestHistoryEntity();
            getRepository().saveAll(List.of(entity1, entity2));

            // When
            List<H> allEntities = getRepository().findAll();

            // Then
            assertThat(allEntities).hasSize(2);
        }
    }

    @Test
    @DisplayName("Should maintain data integrity across operations")
    void dataIntegrity_ShouldBeMaintained() {
        // Given
        UUID originalId = UUID.randomUUID();
        H entity = createTestHistoryEntity(originalId);
        entity.setSynced(false);

        // When - Save
        H saved = getRepository().save(entity);

        // When - Mark as synced
        int updateCount = getRepository().markAsSynced((ID) saved.getId());

        // CRITICAL: Clear Hibernate cache to see the updated state
        entityManager.flush();
        entityManager.clear();

        // When - Find by original ID
        List<H> results = getRepository().findByOriginalId(originalId);

        // Then
        assertThat(updateCount).isEqualTo(1);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).isSynced()).isTrue();
        assertThat(results.get(0).getOriginalId()).isEqualTo(originalId);
    }
}

