package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageStatus {
    New(0),
    Ready(4),
    Sending(5),
    Complete(6),
    Failed(7);

    private final int code;

    public static MessageStatus fromCode(int code) {
        for (MessageStatus status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Unknown MessageStatus code: " + code);
    }
}
