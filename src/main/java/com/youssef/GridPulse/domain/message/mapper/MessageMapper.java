package com.youssef.GridPulse.domain.message.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.configuration.mapping.BaseMappingConfig;
import com.youssef.GridPulse.domain.message.dto.MessageInput;
import com.youssef.GridPulse.domain.message.dto.UpdateMessageInput;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import org.mapstruct.*;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(config = BaseMappingConfig.class)
public interface MessageMapper extends BaseMapper<Message, MessageHistory, MessageInput> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device.id", source = "deviceId")
    @Mapping(target = "device", ignore = true)
    @Mapping(target = "explanation", ignore = true)
    @Mapping(target = "severity", ignore = true)

    Message toEntity(MessageInput input);

    @Override
    @Mapping(target = "originalId", source = "id") // link to original inverter
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")

    @Mapping(source = "device.id", target = "deviceId")
    MessageHistory toHistory(Message entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    @Mapping(target = "messageText", ignore = true)
    @Mapping(target = "explanation", ignore = true)
    @Mapping(target = "severity", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "receivedAt", ignore = true)
    void updateEntity(MessageInput input, @MappingTarget Message entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")

    @Mapping(target = "device", ignore = true)
    @Mapping(target = "messageText", ignore = true)
    @Mapping(target = "messageType", ignore = true)
    @Mapping(target = "explanation", ignore = true)
    @Mapping(target = "severity", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "receivedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateMessageInput input, @MappingTarget Message entity);
}
