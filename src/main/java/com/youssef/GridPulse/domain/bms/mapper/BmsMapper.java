package com.youssef.GridPulse.domain.bms.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.bms.dto.BmsInput;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.entity.BmsHistory;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface BmsMapper extends BaseMapper<Bms, BmsHistory, BmsInput> {

}
