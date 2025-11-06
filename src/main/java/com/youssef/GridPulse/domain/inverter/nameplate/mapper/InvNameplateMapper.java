package com.youssef.GridPulse.domain.inverter.nameplate.mapper;

import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.configuration.mapping.InverterReferenceMapper;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.nameplate.dto.InvNameplateInput;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplateHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class, uses = {InverterReferenceMapper.class})
public interface InvNameplateMapper extends SunSpecModelMapper<InvNameplate, InvNameplateHistory, InvNameplateInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "modelId", constant = "120")
    @Mapping(target = "inverter", source = "inverterId")
    InvNameplate toEntity(InvNameplateInput input);

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
    void updateEntity(InvNameplateInput input, @MappingTarget InvNameplate entity);
}


