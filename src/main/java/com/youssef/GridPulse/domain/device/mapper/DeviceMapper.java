package com.youssef.GridPulse.domain.device.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface DeviceMapper extends BaseMapper<Device, DeviceHistory, DeviceInput> {

    @Override
    @Mapping(target = "originalId", source = "id") // link to original inverter
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    // Specific mappings for related entity IDs
    @Mapping(source = "fleet.id", target = "fleetId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "inverter.id", target = "inverterId")
    @Mapping(source = "bms.id", target = "bmsId")
    @Mapping(source = "meter.id", target = "meterId")
    DeviceHistory toHistory(Device entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")
    // Specific mappings for related entity IDs
    @Mapping(source = "fleetId", target = "fleet.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "operatorId", target = "operator.id")
    @Mapping(source = "inverterId", target = "inverter.id")
    @Mapping(source = "bmsId", target = "bms.id")
    @Mapping(source = "meterId", target = "meter.id")
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "messages", ignore = true)
    Device toEntity(DeviceInput input);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")
    // Specific mappings for related entity IDs
    @Mapping(source = "fleetId", target = "fleet.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "operatorId", target = "operator.id")
    @Mapping(source = "inverterId", target = "inverter.id")
    @Mapping(source = "bmsId", target = "bms.id")
    @Mapping(source = "meterId", target = "meter.id")
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "messages", ignore = true)
    void updateEntity(DeviceInput input, @MappingTarget Device entity);

}
