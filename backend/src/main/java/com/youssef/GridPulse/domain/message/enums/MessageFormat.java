package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum MessageFormat {
    TEXT(0),   // MQTT
    HEX(2);    // Satellite

    private final int code;

    public static MessageFormat fromCode(int code) {
        return Arrays.stream(values())
                .filter(f -> f.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown format code: " + code));
    }
}
