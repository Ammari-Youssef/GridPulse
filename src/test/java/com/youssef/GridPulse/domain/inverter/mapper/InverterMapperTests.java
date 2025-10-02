package com.youssef.GridPulse.domain.inverter.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.base.BaseMapperTest;
import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;

import java.time.Instant;
import java.util.UUID;

public class InverterMapperTests extends BaseMapperTest<Inverter, InverterHistory, InverterInput> {

    private final InverterMapper mapper = new InverterMapperImpl();

    @Override
    protected BaseMapper<Inverter, InverterHistory, InverterInput> getMapper() {
        return mapper;
    }

    @Override
    protected InverterInput createTestInput() {
        return new InverterInput(
                "Test Inverter",
                "TX5000",
                "v2.1",
                "SolarEdge"
        );
    }

    @Override
    protected Inverter createTestEntity() {
        return Inverter.builder()
                .id(UUID.randomUUID())
                .name("Test Inverter")
                .model("TX5000")
                .version("v2.1")
                .manufacturer("SolarEdge")
                .source("app")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Override
    protected InverterHistory createTestHistoryEntity() {
        return InverterHistory.builder()
                .id(UUID.randomUUID())
                .originalId(UUID.randomUUID())
                .name("Test Inverter")
                .model("TX5000")
                .version("v2.1")
                .manufacturer("SolarEdge")
                .build();
    }

    @Override
    protected Inverter cloneEntity(Inverter entity) {
        return Inverter.builder()
                .id(entity.getId())
                .name(entity.getName())
                .model(entity.getModel())
                .version(entity.getVersion())
                .manufacturer(entity.getManufacturer())
                .source(entity.getSource())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}