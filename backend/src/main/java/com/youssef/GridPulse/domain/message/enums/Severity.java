package com.youssef.GridPulse.domain.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Severity {

    CRITICAL(0), // 0
    MEDIUM(1),   // 1
    LOW(2),      // 2
    INFO(3);     // 3

    private final int level;

    public static Severity fromCode(int code) {
        for (Severity s : values()) {
            if (s.level == code) return s;
        }
        throw new IllegalArgumentException("Unknown severity code: " + code);
    }

}
