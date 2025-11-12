package com.youssef.GridPulse.domain.security.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.security.dto.ImportSecurityKeyInput;
import com.youssef.GridPulse.domain.security.dto.SecurityKeyInput;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.entity.SecurityKeyHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface SecurityKeyMapper extends BaseMapper<SecurityKey, SecurityKeyHistory, SecurityKeyInput> {

    @Override
    @Mapping(target = "originalId", source = "id") // link to original security key record
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "owner.id", target = "ownerId")
    SecurityKeyHistory toHistory(SecurityKey entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(source = "deviceId", target = "device.id")
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(target = "privateKey", ignore = true)  // This is excluded from general input
    SecurityKey toEntity(SecurityKeyInput input);

    // Specific mapping for imported security key creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(source = "deviceId", target = "device.id")
    @Mapping(source = "ownerId", target = "owner.id")
    SecurityKey toEntity(ImportSecurityKeyInput input); // Not used currently in importedSecurityKey creation

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(source = "deviceId", target = "device.id")
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(target = "privateKey", ignore = true)
    void updateEntity(SecurityKeyInput securityKeyInput, @MappingTarget SecurityKey entity);

}
