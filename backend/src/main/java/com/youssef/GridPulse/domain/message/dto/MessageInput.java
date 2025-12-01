package com.youssef.GridPulse.domain.message.dto;

import com.youssef.GridPulse.domain.message.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageInput(
        @NotNull UUID deviceId,
        @NotBlank String cloudMessageId,
        @NotBlank Object messageText, // Raw JSON string
        @NotNull MessageType messageType,
        @NotNull MessageFormat format,
        @NotNull MessageStatus status,
        @NotNull MessagePriority priority,
        @NotNull OffsetDateTime sentAt,
        @NotNull OffsetDateTime receivedAt
) {}




