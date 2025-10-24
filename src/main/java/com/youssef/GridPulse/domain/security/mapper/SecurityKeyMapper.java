package com.youssef.GridPulse.domain.security.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.security.dto.ImportSecurityKeyInput;
import com.youssef.GridPulse.domain.security.dto.SecurityKeyInput;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.entity.SecurityKeyHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SecurityKeyMapper extends BaseMapper<SecurityKey, SecurityKeyHistory, SecurityKeyInput> {

    @Override
    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "owner.id", target = "ownerId")
    SecurityKeyHistory toHistory(SecurityKey entity);

    @Override
    @Mapping(source = "deviceId", target = "device.id")
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(target = "privateKey", ignore = true) // This is excluded from general input
    SecurityKey toEntity(SecurityKeyInput input);

    @Mapping(source = "deviceId", target = "device.id")
    @Mapping(source = "ownerId", target = "owner.id")
    SecurityKey toEntity(ImportSecurityKeyInput input); // Not used currently in importedSecurityKey creation

}
