package com.youssef.GridPulse.domain.inverter.repository;

import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.utils.TestSuiteUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test class for {@link InverterRepository}.
 *
 * <p>This class sets up the testing environment and provides lifecycle logging for each test.
 * Add tests here only when implementing custom query methods in the repository.</p>
 *
 * <p>Common CRUD operations provided by Spring Data JPA do not need to be tested
 * as they are thoroughly tested by the Spring Data team.</p>
 *
 * @see InverterRepository
 */

@DataJpaTest
class InverterRepositoryTest {

    @Autowired
    private InverterRepository repository;

    private static Instant suiteStartTime;
    private static int testCounter = 1;


    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        System.out.println("\n⭐ InverterRepository Test Execution Started");
        System.out.println("⏰ Start Time: " + suiteStartTime);
        System.out.println("-".repeat(50));
    }

    @AfterAll
    static void endTestExecution() {
        Instant suiteEndTime = Instant.now();
        long duration = Duration.between(suiteStartTime, suiteEndTime).toMillis();

        System.out.println("-".repeat(50));
        System.out.println("🏁 InverterRepositoryTest Test Execution Completed");
        System.out.println("⏰ End Time: " + suiteEndTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(50));
    }

    @BeforeEach
    void setUp() {
        System.out.println("📋 Test " + testCounter + " - Setting up...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Test " + testCounter + " - Completed");
        testCounter += 1;
    }

    @Test
    void saveAndFindById_ShouldPersistEntity() {
        // Given: use utility method
        Inverter inverter = TestSuiteUtils.createTestInverterHibernate();

        // When
        Inverter saved = repository.save(inverter);

        // Then
        Optional<Inverter> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Inverter");
        assertThat(found.get().getManufacturer()).isEqualTo("Test Manufacturer");
    }

    @Test
    void findTopNOrderByCreatedAtDesc_ShouldReturnLatestEntities() {
        // Given: two inverters with different timestamps
        Inverter inv1 = Inverter.builder()
                .name("Test Inverter A")
                .manufacturer("Manufacturer A")
                .model("Model A")
                .version("1.0")
                .createdAt(OffsetDateTime.now().minusDays(2))
                .build();

        repository.saveAndFlush(inv1);

        Inverter inv2 = Inverter.builder()
                .name("Test Inverter B")
                .manufacturer("Manufacturer B")
                .model("Model B")
                .version("1.0")
                .createdAt(OffsetDateTime.now().minusDays(1))
                .build();
        repository.saveAndFlush(inv2);

        // When
        List<Inverter> result = repository.findTopNOrderByCreatedAtDesc(1);

        // Then
        assertThat(result.size()).isEqualTo(1);

        }

}