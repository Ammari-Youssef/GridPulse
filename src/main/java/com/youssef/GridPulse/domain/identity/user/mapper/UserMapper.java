package com.youssef.GridPulse.domain.identity.user.mapper;

import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(config = BaseMappingConfig.class)
public interface UserMapper {

    // 1️⃣ Entity → History
    @Mapping(target = "id", ignore = true) // history has its own ID
    @Mapping(target = "originalId", source = "id") // link to original user
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    @Mapping(target = "operatedDeviceIds", source = "operatedDevices")
    @Mapping(target = "usedDeviceIds", source = "usedDevices")
    UserHistory toHistory(User user);

    // 2️⃣ DTO → Entity (Register)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "source", constant = "APP") // default source
    @Mapping(target = "role", constant = "USER") // default role
    @Mapping(target = "securityKeys", ignore = true)
    @Mapping(target = "usedDevices", ignore = true)
    @Mapping(target = "operatedDevices", ignore = true)
    User toEntity(RegisterInput registerInput);

    // 3️⃣ Custom mapping for devices to device IDs
    default List<UUID> map(List<Device> devices) {
        if (devices == null) return null;
        return devices.stream()
                .map(Device::getId)
                .toList();
    }


}