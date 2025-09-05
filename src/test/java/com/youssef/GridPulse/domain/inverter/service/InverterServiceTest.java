package com.youssef.GridPulse.domain.inverter.service;

import com.youssef.GridPulse.domain.base.BaseServiceTest;
import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.mapper.InverterMapperImpl;
import com.youssef.GridPulse.domain.inverter.repository.InverterHistoryRepository;
import com.youssef.GridPulse.domain.inverter.repository.InverterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.time.Instant;
import java.util.UUID;

class InverterServiceTest extends BaseServiceTest<Inverter, InverterHistory, UUID, InverterInput, InverterService> {

    @Mock
    private InverterRepository inverterRepository;

    @Mock
    private InverterHistoryRepository inverterHistoryRepository;

    @Mock
    private InverterMapperImpl inverterMapper;

    public InverterServiceTest() {
        super(null, null, null); // Will be set in @BeforeEach
    }

    @BeforeEach
    void setUpMocks() {
        // Set the parent class fields
        repository = inverterRepository;
        historyRepository = inverterHistoryRepository;
        mapper = inverterMapper;
    }

    @Override
    protected InverterService createService() {
        return new InverterService(inverterRepository, inverterHistoryRepository, inverterMapper);
    }

    @Override
    protected Inverter createTestEntity(UUID originalId) {
        return Inverter.builder()
                .id(originalId)
                .name("Test Inverter")
                .model("TX5000")
                .manufacturer("SolarEdge")
                .version("v2.1")
                .build();
    }

    @Override
    protected InverterHistory createTestHistoryEntity(UUID originalId) {
        return InverterHistory.builder()
                .originalId(originalId)
                .name("Test Inverter")
                .manufacturer("Test Manufacturer")
                .model("Test Model")
                .version("Test Version")
                .synced(false)
                .createdAt(Instant.now())
                .build();
    }

    @Override
    protected InverterInput createTestInput() {
        return  new InverterInput("Test Inverter", "TX5000", "v2.1", "SolarEdge");
    }

    @Override
    protected UUID createTestId() {
        return UUID.randomUUID();
    }
}