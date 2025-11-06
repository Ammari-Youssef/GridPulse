package com.youssef.GridPulse.domain.inverter.inverter.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.UUID;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface InverterMapper extends BaseMapper<Inverter, InverterHistory, InverterInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "commonList", ignore = true)
    @Mapping(target = "settingsList", ignore = true)
    Inverter toEntity(InverterInput inverterInput);

    @Override
    @Mapping(target = "originalId", source = "id") // link to original inverter
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")

    @Mapping(target = "deviceIds", source = "devices")
    InverterHistory toHistory(Inverter entity);


    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "commonList", ignore = true)
    @Mapping(target = "settingsList", ignore = true)
    void updateEntity(InverterInput inverterInput, @MappingTarget Inverter entity);

    // Custom mapping for devices to device IDs
    default List<UUID> map(List<Device> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(Device::getId)
                .toList();
    }

}