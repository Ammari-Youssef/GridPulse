package com.youssef.GridPulse.domain.inverter.mapper;

import com.youssef.GridPulse.domain.base.BaseMapper;
import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface InverterMapper extends BaseMapper<Inverter, InverterHistory, InverterInput> {}
