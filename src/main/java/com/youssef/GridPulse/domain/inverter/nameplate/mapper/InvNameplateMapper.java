package com.youssef.GridPulse.domain.inverter.nameplate.mapper;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.nameplate.dto.InvNameplateInput;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplateHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvNameplateMapper extends SunSpecModelMapper<InvNameplate, InvNameplateHistory, InvNameplateInput> {

    @Override
    @Mapping(target = "modelId", constant = "120")
    InvNameplate toEntity(InvNameplateInput input);
}
