package com.youssef.GridPulse.domain.message.dto;

import com.youssef.GridPulse.domain.message.enums.MessageFormat;
import com.youssef.GridPulse.domain.message.enums.MessagePriority;
import com.youssef.GridPulse.domain.message.enums.MessageStatus;

public record UpdateMessageInput(
        String cloudMessageId,
        Object messageText, // Raw JSON string
        MessageFormat format,
        MessageStatus status,
        MessagePriority priority
) {
}




