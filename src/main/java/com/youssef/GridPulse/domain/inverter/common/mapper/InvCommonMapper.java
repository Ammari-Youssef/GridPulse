package com.youssef.GridPulse.domain.inverter.common.mapper;

import com.youssef.GridPulse.configuration.mapping.InverterReferenceMapper;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.common.dto.InvCommonInput;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommonHistory;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;


@Primary
@Mapper(config = BaseMappingConfig.class, uses = {InverterReferenceMapper.class})
public interface InvCommonMapper extends SunSpecModelMapper<InvCommon, InvCommonHistory, InvCommonInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "modelId", constant = "1")
    @Mapping(target = "inverter", source = "inverterId")
    InvCommon toEntity(InvCommonInput input);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "modelId", ignore = true)
    @Mapping(target = "inverter", source = "inverterId")
    void updateEntity(InvCommonInput input, @MappingTarget InvCommon entity);

}
