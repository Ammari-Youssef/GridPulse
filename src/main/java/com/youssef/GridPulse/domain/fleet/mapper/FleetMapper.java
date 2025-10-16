package com.youssef.GridPulse.domain.fleet.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.entity.FleetHistory;
import com.youssef.GridPulse.domain.fleet.dto.FleetInput;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FleetMapper extends BaseMapper<Fleet, FleetHistory, FleetInput> {
}
