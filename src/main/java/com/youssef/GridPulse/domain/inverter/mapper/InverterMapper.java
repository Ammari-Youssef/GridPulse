package com.youssef.GridPulse.domain.inverter.mapper;

import com.youssef.GridPulse.domain.base.BaseMapper;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InverterMapper extends BaseMapper<Inverter, InverterHistory, InverterInput> {

    @Override
    @Mapping(target = "deviceIds", source = "devices")
    InverterHistory toHistory(Inverter entity);

    // Custom mapping for devices to device IDs
    default List<java.util.UUID> map(List<Device> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(Device::getId)
                .toList();
    }

}