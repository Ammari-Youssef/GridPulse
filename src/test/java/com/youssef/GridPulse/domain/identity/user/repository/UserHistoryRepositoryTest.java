package com.youssef.GridPulse.domain.identity.user.repository;

import com.youssef.GridPulse.configuration.graphql.GraphQLConfig;
import com.youssef.GridPulse.domain.base.BaseHistoryRepositoryTest;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.utils.TestLogger;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GraphQLConfig.class)
class UserHistoryRepositoryTest {

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    // Test counter
    private static int testCounter = 1;
    private static Instant suiteStartTime;

    @Autowired
    private TestEntityManager entityManager; // Better for setup

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
        userHistoryRepository.deleteAll();
        testCounter++;
    }


    @Test
    @Transactional
        // Required for @Modifying queries
    void markAsSynced_WhenIdExists_UpdatesRecord() {
        // Given
        UserHistory history = UserHistory.builder()
                .originalId(UUID.randomUUID())
                .synced(false)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();

        entityManager.persist(history);
        entityManager.flush();  // Ensure initial state is persisted

        // When
        int updatedCount = userHistoryRepository.markAsSynced(history.getId());

        entityManager.flush();
        entityManager.clear();

        // Then
        assertEquals(1L, updatedCount);
        // Verify the update actually happened
        UserHistory updated = entityManager.find(UserHistory.class, history.getId());
        assertThat(updated.isSynced()).isTrue();
    }

    @Test
    @Transactional
    void markAsSynced_WhenIdNotExists_ReturnsZero() {
        // When
        int updatedCount = userHistoryRepository.markAsSynced(UUID.randomUUID());

        // Then
        assertThat(updatedCount).isEqualTo(0);
    }

    @Test
    void findByOriginalId_WhenRecordsExist_ReturnsList() {
        // Given
        UUID originalId = UUID.randomUUID();

        UserHistory history1 = UserHistory.builder()
                .originalId(originalId)
                .synced(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();

        UserHistory history2 = UserHistory.builder()
                .originalId(originalId)  // Same originalId
                .synced(false)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();

        UserHistory otherHistory = UserHistory.builder()
                .originalId(UUID.randomUUID())  // Different originalId
                .synced(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();

        entityManager.persist(history1);
        entityManager.persist(history2);
        entityManager.persist(otherHistory);
        entityManager.flush();

        // When
        List<UserHistory> result = userHistoryRepository.findByOriginalId(originalId);

        // Then
        assertThat(result)
                .hasSize(2)
                .extracting(UserHistory::getOriginalId)
                .containsOnly(originalId);
    }

    @Test
    void findByOriginalId_WhenNoRecordsExist_ReturnsEmptyList() {
        // When
        List<UserHistory> result = userHistoryRepository.findByOriginalId(UUID.randomUUID());

        // Then
        assertThat(result).isEmpty();
    }
}