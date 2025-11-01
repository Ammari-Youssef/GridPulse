package com.youssef.GridPulse.domain.inverter.common.mapper;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelMapper;
import com.youssef.GridPulse.domain.inverter.common.dto.InvCommonInput;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommonHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvCommonMapper extends SunSpecModelMapper<InvCommon, InvCommonHistory, InvCommonInput> {

    @Override
    @Mapping(target = "modelId", constant = "1")
    InvCommon toEntity(InvCommonInput input);
}
