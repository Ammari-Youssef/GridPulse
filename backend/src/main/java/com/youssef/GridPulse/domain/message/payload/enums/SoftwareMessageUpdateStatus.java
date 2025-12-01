package com.youssef.GridPulse.domain.message.payload.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Enum representing the status of a software typed message's payload.
 * {@link com.youssef.GridPulse.domain.message.payload.SoftwarePayload}.
 */

@Getter
@RequiredArgsConstructor
public enum SoftwareMessageUpdateStatus {

    SUCCESS(200),
    FAILURE(500);

    private final int code;

    public static SoftwareMessageUpdateStatus fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status code: " + code));
    }
}
