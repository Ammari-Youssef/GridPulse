package com.youssef.GridPulse.domain.inverter.settings.mapper;

import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.configuration.mapping.InverterReferenceMapper;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.settings.dto.InvSettingsInput;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettingsHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class, uses = {InverterReferenceMapper.class})
public interface InvSettingsMapper extends SunSpecModelMapper<InvSettings, InvSettingsHistory, InvSettingsInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "modelId", constant = "121")
    @Mapping(target = "inverter", source = "inverterId")
    InvSettings toEntity(InvSettingsInput input);

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
    void updateEntity(InvSettingsInput input, @MappingTarget InvSettings entity);
}
