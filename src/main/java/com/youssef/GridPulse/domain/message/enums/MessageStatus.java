package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageStatus {
    NEW(0),
    READY(4),
    SENDING(5),
    COMPLETE(6),
    FAILED(7);

    private final int code;

    public static MessageStatus fromCode(int code) {
        for (MessageStatus status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Unknown MessageStatus code: " + code);
    }
}
