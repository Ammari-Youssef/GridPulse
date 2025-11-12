package com.youssef.GridPulse.domain.identity.user.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.dto.UpdateUserInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.UUID;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface UserMapper extends BaseMapper<User, UserHistory, RegisterInput> {

    // 1️⃣ Entity → History
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originalId", source = "id")
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    @Mapping(target = "operatedDeviceIds", source = "operatedDevices")
    @Mapping(target = "usedDeviceIds", source = "usedDevices")
    UserHistory toHistory(User user);

    // 2️⃣ DTO → Entity (Register)
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "source", constant = "APP")
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "usedDevices", ignore = true)
    @Mapping(target = "operatedDevices", ignore = true)
    User toEntity(RegisterInput registerInput);

    // 3️⃣ Partial update (UpdateUserInput)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "usedDevices", ignore = true)
    @Mapping(target = "operatedDevices", ignore = true)
    void updateEntity(UpdateUserInput input, @MappingTarget User entity);


    // This is to remove Mapstructs warnings about ambiguous mapping methods
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "usedDevices", ignore = true)
    @Mapping(target = "operatedDevices", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateEntity(RegisterInput input, @MappingTarget User entity);

    // 4️⃣ Custom mapping for devices to device IDs
    default List<UUID> map(List<Device> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(Device::getId)
                .toList();
    }
}
