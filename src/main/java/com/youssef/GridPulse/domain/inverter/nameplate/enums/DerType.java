package com.youssef.GridPulse.domain.inverter.nameplate.enums;

import lombok.Getter;

@Getter
public enum DerType {
    PV(4, "Photovoltaic device"),
    PV_STORAGE(82, "Photovoltaic + Storage device");

    private final int value;
    private final String description;

    DerType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static DerType fromValue(int value) {
        for (DerType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DER type value: " + value);
    }
}

