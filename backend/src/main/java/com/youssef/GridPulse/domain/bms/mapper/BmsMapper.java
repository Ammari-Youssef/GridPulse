package com.youssef.GridPulse.domain.bms.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.bms.dto.BmsInput;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.entity.BmsHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface BmsMapper extends BaseMapper<Bms, BmsHistory, BmsInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    Bms toEntity(BmsInput input);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    void updateEntity(BmsInput bmsInput, @MappingTarget Bms entity);
}
