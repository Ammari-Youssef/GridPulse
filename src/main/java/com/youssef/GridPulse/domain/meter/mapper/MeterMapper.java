package com.youssef.GridPulse.domain.meter.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.meter.dto.MeterInput;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.entity.MeterHistory;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface MeterMapper extends BaseMapper<Meter, MeterHistory, MeterInput> {
}
