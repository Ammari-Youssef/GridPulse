package com.youssef.GridPulse.domain.fleet.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.entity.FleetHistory;
import com.youssef.GridPulse.domain.fleet.dto.FleetInput;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface FleetMapper extends BaseMapper<Fleet, FleetHistory, FleetInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "devices", ignore = true)
    Fleet toEntity(FleetInput input);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "devices", ignore = true)
    void updateEntity(FleetInput input, @MappingTarget Fleet entity);

}
