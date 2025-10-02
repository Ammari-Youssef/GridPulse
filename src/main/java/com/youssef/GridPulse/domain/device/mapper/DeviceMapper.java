package com.youssef.GridPulse.domain.device.mapper;

import com.youssef.GridPulse.domain.base.BaseMapper;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface DeviceMapper extends BaseMapper<Device, DeviceHistory, DeviceInput> {
    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "inverter.id", target = "inverterId")
    DeviceHistory toHistory(Device entity);
}
