package com.youssef.GridPulse.domain.message.payload.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * This enum is used to represent different types of software packages.
 * Each type is associated with a specific code.
 * It is used in @link SoftwarePayload
 * Enum representing the status of a software typed message's payload.
 * {@link com.youssef.GridPulse.domain.message.payload.SoftwarePayload}.
 */
 
@Getter
@RequiredArgsConstructor
public enum SoftwarePackageType {
    OS(0),
    COMMS(1),
    IDS(2),
    BMS(3),
    INV(4),
    METER(5),
    API(6);

    private final int code;


    public static SoftwarePackageType fromCode(int code) {
        return Arrays.stream(values())
                .filter(p -> p.code == code )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown package type: " + code));
    }
}

