package com.youssef.GridPulse.domain.device.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceMapper extends BaseMapper<Device, DeviceHistory, DeviceInput> {

    @Override
    @Mapping(source = "fleet.id", target = "fleetId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "inverter.id", target = "inverterId")
    @Mapping(source = "bms.id", target = "bmsId")
    @Mapping(source = "meter.id", target = "meterId")
    DeviceHistory toHistory(Device entity);

    @Override
    @Mapping(source = "fleetId", target = "fleet.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "operatorId", target = "operator.id")
    @Mapping(source = "inverterId", target = "inverter.id")
    @Mapping(source = "bmsId", target = "bms.id")
    @Mapping(source = "meterId", target = "meter.id")
    Device toEntity(DeviceInput input);


}
