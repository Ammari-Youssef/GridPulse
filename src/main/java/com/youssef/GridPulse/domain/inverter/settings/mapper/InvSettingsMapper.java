package com.youssef.GridPulse.domain.inverter.settings.mapper;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.settings.dto.InvSettingsInput;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettingsHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvSettingsMapper extends SunSpecModelMapper<InvSettings, InvSettingsHistory, InvSettingsInput> {

    @Override
    @Mapping(target = "modelId", constant = "121")
    InvSettings toEntity(InvSettingsInput input);
}
