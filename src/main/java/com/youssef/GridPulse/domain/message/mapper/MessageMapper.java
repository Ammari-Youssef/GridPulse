package com.youssef.GridPulse.domain.message.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.message.dto.MessageInput;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;


@Primary
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper extends BaseMapper<Message, MessageHistory, MessageInput> {

    @Override
    @Mapping(source = "device.id", target = "deviceId")
    MessageHistory toHistory(Message entity);

}
