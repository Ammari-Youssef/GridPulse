package com.youssef.GridPulse.domain.inverter.settings.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ConnPhase {
    A(1),
    B(2),
    C(3);

    private final int value;

    public static ConnPhase fromValue(int value) {
        return Arrays.stream(values())
                .filter(p -> p.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ConnPhase value: " + value));
    }
}
