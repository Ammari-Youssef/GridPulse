package com.youssef.GridPulse.domain.inverter.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.base.BaseHistoryRepositoryTest;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

class InverterHistoryRepositoryTest extends BaseHistoryRepositoryTest<InverterHistory, UUID> {

    @Autowired
    private InverterHistoryRepository inverterHistoryRepository;

    @Override
    protected BaseHistoryRepository<InverterHistory, UUID> getRepository() {
        return inverterHistoryRepository;
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
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    @Override
    protected InverterHistory createTestHistoryEntity() {
        return createTestHistoryEntity(UUID.randomUUID());
    }

    @Override
    protected Class<InverterHistory> getEntityClass() {
        return InverterHistory.class;
    }
}