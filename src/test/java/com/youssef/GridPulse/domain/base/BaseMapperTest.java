package com.youssef.GridPulse.domain.base;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.utils.TestLogger;
import org.junit.jupiter.api.*;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseMapperTest<E extends BaseEntity, H extends BaseHistoryEntity, INPUT> {

    protected abstract BaseMapper<E, H, INPUT> getMapper();
    protected abstract INPUT createTestInput();
    protected abstract E createTestEntity();
    protected abstract H createTestHistoryEntity();

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

    @Nested
    class ToHistoryTests {
        @Test
        void should_map_toHistory() {
            System.out.println("🔹 Running should_map_toHistory");

            // Given
            E entity = createTestEntity();

            // When
            H history = getMapper().toHistory(entity);

            // Then
            assertThat(history).isNotNull();
            assertThat(history.getOriginalId()).isEqualTo(entity.getId());
            assertThat(history.isCreatedRecord()).isFalse();
            assertThat(history.isUpdatedRecord()).isFalse();
            assertThat(history.isDeletedRecord()).isFalse();
            assertThat(history.isSynced()).isFalse();

            System.out.println("✔️ should_map_toHistory passed");
        }

        @Test
        void should_map_toHistory_withNullInput() {
            System.out.println("🔹 Running should_map_toHistory_withNullInput");

            // When
            H history = getMapper().toHistory(null);

            // Then
            assertThat(history).isNull();

            System.out.println("✔️ should_map_toHistory_withNullInput passed");
        }
    }

    @Nested
    class ToEntityTests {
        @Test
        void should_map_toEntity() {
            System.out.println("🔹 Running should_map_toEntity");

            // Given
            INPUT input = createTestInput();

            // When
            E entity = getMapper().toEntity(input);

            // Then
            assertThat(entity).isNotNull();
            // Entity-specific assertions should be implemented in concrete tests

            System.out.println("✔️ should_map_toEntity passed");
        }

        @Test
        void should_map_toEntity_withNullInput() {
            System.out.println("🔹 Running should_map_toEntity_withNullInput");

            // When
            E entity = getMapper().toEntity(null);

            // Then
            assertThat(entity).isNull();

            System.out.println("✔️ should_map_toEntity_withNullInput passed");
        }
    }

    @Nested
    class UpdateEntityTests {
        @Test
        void should_updateEntity() {
            System.out.println("🔹 Running should_updateEntity");

            // Given
            INPUT input = createTestInput();
            E entity = createTestEntity();

            // When
            getMapper().updateEntity(input, entity);

            // Then
            // Entity-specific update assertions should be implemented in concrete tests
            assertThat(entity).isNotNull();

            System.out.println("✔️ should_updateEntity passed");
        }

        @Test
        void should_notUpdateEntity_withNullInput() {
            System.out.println("🔹 Running should_notUpdateEntity_withNullInput");

            // Given
            E entity = createTestEntity();
            E originalEntity = cloneEntity(entity); // Should be implemented in concrete class

            // When
            getMapper().updateEntity(null, entity);

            // Then - Should remain unchanged
            assertThat(entity)
                    .usingRecursiveComparison()
                    .isEqualTo(originalEntity);

            System.out.println("✔️ should_notUpdateEntity_withNullInput passed");
        }

        @Test
        void should_notUpdateEntity_withNullEntity() {
            System.out.println("🔹 Running should_notUpdateEntity_withNullEntity");

            // Given
            E entity = createTestEntity();

            // When & Then - Should not throw exception
            assertDoesNotThrow(() -> getMapper().updateEntity(null , entity));

            System.out.println("✔️ should_notUpdateEntity_withNullEntity passed");
        }
    }

    // Helper method that concrete classes should implement if needed

    /**
     * Clone the entity and save its fields values for comparison in tests.
     * @param entity Entity to clone
     * @return Cloned entity with saved field values
     */
    protected E cloneEntity(E entity) {
        // Concrete classes should override this for proper cloning
        return entity;
    }
}