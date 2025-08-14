package com.youssef.GridPulse.domain.mapper;

import com.youssef.GridPulse.domain.dto.InverterInput;
import com.youssef.GridPulse.domain.entity.Inverter;
import com.youssef.GridPulse.domain.entity.InverterHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InverterMapper {
    // 1️⃣ Entity → History
    @Mapping(target = "id", ignore = true) // history has its own ID
    @Mapping(target = "originalId", source = "id") // link to original user
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    InverterHistory toHistory(Inverter user);

    // 2️⃣ DTO → Entity (Register)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "app")
    Inverter toEntity(InverterInput input);

}
