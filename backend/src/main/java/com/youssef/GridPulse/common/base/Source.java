package com.youssef.GridPulse.common.base;

import lombok.Getter;

@Getter
public enum Source {

    APP("app"),
    SYNC("sync"),
    CLOUD("cloud"),
    DEVICE("device"),
    GATEWAY("gateway");

    private final String value;

    // ✅ Add this constructor
    Source(String value) {
        this.value = value;
    }

}
