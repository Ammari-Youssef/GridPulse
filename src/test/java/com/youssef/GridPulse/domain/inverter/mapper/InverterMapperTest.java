package com.youssef.GridPulse.domain.inverter.mapper;

import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.mapper.InverterMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InverterMapperTest {

    private InverterMapper mapper;

    private static Instant suiteStartTime;
    private static int testCounter = 1;

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        System.out.println("\n⭐ InverterMapper Test Execution Started");
        System.out.println("⏰ Start Time: " + suiteStartTime);
        System.out.println("-".repeat(50));
    }

    @AfterAll
    static void endTestExecution() {
        Instant suiteEndTime = Instant.now();
        long duration = Duration.between(suiteStartTime, suiteEndTime).toMillis();

        System.out.println("-".repeat(50));
        System.out.println("🏁 InverterMapper Test Execution Completed");
        System.out.println("⏰ End Time: " + suiteEndTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(50));
    }

    @BeforeEach
    void setUp() {
        System.out.println("📋 Test " + testCounter + " - Setting up...");
        mapper = new InverterMapperImpl();

    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Test " + testCounter + " - Completed");
        testCounter += 1;
    }

    @Nested
    class ToHistoryTests{
        @Test
        void should_map_toHistory() {
            System.out.println("🔹 Running should_map_toHistory");

            // Given
            var inverter = Inverter.builder()
                    .id(UUID.randomUUID())
                    .name("Main Inverter")
                    .model("ModelX")
                    .manufacturer("InverterCo")
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            // When
            var history = mapper.toHistory(inverter);

            // Then
            assertNotNull(history);
            assertNull(history.getId()); // history has its own ID
            assertEquals(inverter.getId(), history.getOriginalId());
            assertEquals(inverter.getModel(), history.getModel());
            assertEquals(inverter.getManufacturer(), history.getManufacturer());
            assertFalse(history.isCreatedRecord());
            assertFalse(history.isUpdatedRecord());
            assertFalse(history.isDeletedRecord());
            assertFalse(history.isSynced());

            System.out.println("✔️ should_map_toHistory passed");
        }

        @Test
        void should_map_toHistory_withNullInput() {
            System.out.println("🔹 Running should_map_toHistory_withNullInput");

            // When
            var history = mapper.toHistory(null);

            // Then
            assertNull(history);

            System.out.println("✔️ should_map_toHistory_withNullInput passed");
        }
    }

    @Nested
    class ToEntityTests{
        @Test
        void should_map_toEntity() {
            System.out.println("🔹 Running should_map_toEntity");

            // Given
            var input = new InverterInput(
                    "Backup Inverter",
                    "ModelY",
                    "1.0",
                    "InverterMakers"
            );

            // When
            var entity = mapper.toEntity(input);

            // Then
            assertNotNull(entity);
            assertNull(entity.getId());
            assertEquals(input.name(), entity.getName());
            assertEquals(input.model(), entity.getModel());
            assertEquals(input.version(), entity.getVersion());
            assertEquals(input.manufacturer(), entity.getManufacturer());
            assertEquals("app", entity.getSource());

            System.out.println("✔️ should_map_toEntity passed");
        }

        @Test
        void should_map_toEntity_withNullInput() {
            System.out.println("🔹 Running should_map_toEntity_withNullInput");

            // When
            var entity = mapper.toEntity(null);

            // Then
            assertNull(entity);

            System.out.println("✔️ should_map_toEntity_withNullInput passed");
        }
    }

    @Nested
    class UpdateEntityTests{
        @Test
        void should_updateEntity() {
            System.out.println("Running should_updateEntity");

            // Given
            var input = new InverterInput(
                    "Updated Inverter",
                    "ModelZ",
                    "2.0",
                    "NewInverterCo"
            );

            var entity = Inverter.builder()
                    .id(UUID.randomUUID())
                    .name("Old Inverter")
                    .model("OldModel")
                    .version("1.0")
                    .manufacturer("OldManufacturer")
                    .build();

            // When
            mapper.updateEntity(input, entity);

            // Then
            assertEquals(input.name(), entity.getName());
            assertEquals(input.model(), entity.getModel());
            assertEquals(input.version(), entity.getVersion());
            assertEquals(input.manufacturer(), entity.getManufacturer());

            System.out.println("should_updateEntity passed");
        }

        @Test
        void should_notUpdateEntity_withNullInput() {
            System.out.println("Running should_notUpdateEntity_withNullInput");

            // Given
            var entity = Inverter.builder()
                    .id(UUID.randomUUID())
                    .name("Old Inverter")
                    .model("OldModel")
                    .version("1.0")
                    .manufacturer("OldManufacturer")
                    .build();

            var originalName = entity.getName();
            var originalModel = entity.getModel();
            var originalVersion = entity.getVersion();
            var originalManufacturer = entity.getManufacturer();

            // When
            mapper.updateEntity(null, entity);

            // Then
            assertEquals(originalName, entity.getName());
            assertEquals(originalModel, entity.getModel());
            assertEquals(originalVersion, entity.getVersion());
            assertEquals(originalManufacturer, entity.getManufacturer());

            System.out.println("should_notUpdateEntity_withNullInput passed");
        }
    }
}