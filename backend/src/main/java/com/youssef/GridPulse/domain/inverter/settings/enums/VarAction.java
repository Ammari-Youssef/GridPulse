package com.youssef.GridPulse.domain.inverter.settings.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum VarAction {
    SWITCH(1),
    MAINTAIN(2);

    private final int value;

    public static VarAction fromValue(int value) {
        return Arrays.stream(values())
                .filter(p -> p.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid VarAction value: " + value));
    }
}


