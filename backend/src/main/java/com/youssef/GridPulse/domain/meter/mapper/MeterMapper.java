package com.youssef.GridPulse.domain.meter.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.meter.dto.MeterInput;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.entity.MeterHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface MeterMapper extends BaseMapper<Meter, MeterHistory, MeterInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    Meter toEntity(MeterInput input);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    void updateEntity(MeterInput meterInput, @MappingTarget Meter entity);
}
