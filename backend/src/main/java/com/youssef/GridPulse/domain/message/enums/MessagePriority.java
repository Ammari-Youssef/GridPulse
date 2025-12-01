package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MessagePriority {
    HIGH(1),
    LOW(4);

    private final int code;

    public static MessagePriority fromCode(int code) {
        return Arrays.stream(values())
                .filter(p -> p.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown priority code: " + code));
    }
}
