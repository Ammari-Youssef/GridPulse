package com.youssef.GridPulse.domain.identity.user.mapper;

import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // 1️⃣ Entity → History
    @Mapping(target = "id", ignore = true) // history has its own ID
    @Mapping(target = "originalId", source = "id") // link to original user
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    UserHistory toHistory(User user);

    // 2️⃣ DTO → Entity (Register)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "app")
    @Mapping(target = "role", constant = "USER") // default role
    User toEntity(RegisterInput registerInput);


}
