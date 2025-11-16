package com.youssef.GridPulse.domain.inverter.service;

import com.youssef.GridPulse.domain.base.BaseServiceTest;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.service.InverterService;
import com.youssef.GridPulse.domain.inverter.inverter.mapper.InverterMapperImpl;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterHistoryRepository;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.utils.TestSuiteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

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
        return TestSuiteUtils.createTestInverterA();
    }

    @Override
    protected InverterHistory createTestHistoryEntity(UUID originalId) {
        return TestSuiteUtils.createTestInverterHistoryA();
    }

    @Override
    protected InverterInput createTestInput() {
        return  TestSuiteUtils.createTestInverterInput();
    }

    @Override
    protected UUID createTestId() {
        return TestSuiteUtils.TEST_INVERTER_ID;
    }
}