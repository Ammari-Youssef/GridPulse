package com.youssef.GridPulse.domain.identity.user.repository;

import com.youssef.GridPulse.configuration.graphql.GraphQLConfig;
import com.youssef.GridPulse.domain.base.BaseHistoryRepositoryTest;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.utils.TestLogger;
import com.youssef.GridPulse.utils.TestSuiteUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GraphQLConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private static int testCounter = 1;
    private static Instant suiteStartTime;

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

    @Test
    void findByEmail_WhenUserExists_ReturnsUser() {
        // given
        User user = TestSuiteUtils.createTestUserHibernateA();
        repository.save(user);

        // when
        Optional<User> result = repository.findByEmail("john.doe@example.com");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getFirstname()).isEqualTo("John");
        assertThat(result.get().getLastname()).isEqualTo("Doe");
    }

    @Test
    void findByEmail_WhenUserNotExists_ReturnsEmpty() {
        // when
        Optional<User> result = repository.findByEmail("nonexistent@example.com");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findTopNOrderByCreatedAtDesc_ShouldReturnLatestEntities() {
        // Given: two users with different timestamps
        User user1 = TestSuiteUtils.createTestUserHibernateA();
        repository.saveAndFlush(user1);

        User user2 = TestSuiteUtils.createTestUserHibernateB();
        repository.saveAndFlush(user2);

        // When
        List<User> result = repository.findTopNOrderByCreatedAtDesc(1);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("jane.smith@example.com");
    }
}
