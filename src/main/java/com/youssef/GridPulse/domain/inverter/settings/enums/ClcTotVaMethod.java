package com.youssef.GridPulse.domain.inverter.settings.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ClcTotVaMethod {
    VECTOR(1),
    ARITHMETIC(2);

    private final int value;

    public static ClcTotVaMethod fromValue(int value) {
        return Arrays.stream(values())
                .filter(p -> p.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ClcTotVaMethod value: " + value));
    }
}

