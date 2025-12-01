package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageType {
    IDS(0),
    HEARTBEAT(1),
    SYSTEM(2),
    SOFTWARE(3),
    BMS(4),
    METER(5),
    INVERTER(6);

    private final int code;

    public static MessageType fromCode(int code) {
        for (MessageType type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Unknown MessageType code: " + code);
    }
}

