package com.youssef.GridPulse.domain.message.payload.enums;

import com.youssef.GridPulse.domain.message.enums.Severity;
import lombok.Getter;

/**
 * Represents known attack types detected by the IDS module.
 * Each type maps to a severity level used in threat classification.
 */
@Getter
public enum AttackType {
    // Critical types
    BACKDOOR(Severity.CRITICAL),
    EXPLOIT(Severity.CRITICAL),
    DOS_DDOS(Severity.CRITICAL),
    WORMS(Severity.CRITICAL),
    // Medium types
    SHELLCODE(Severity.MEDIUM),
    // Low types
    FUZZERS(Severity.LOW),
    GENERIC(Severity.LOW),
    // Info types
    NORMAL(Severity.INFO),
    ANALYSIS(Severity.INFO),
    RECONNAISSANCE(Severity.INFO);

    private final Severity severity;

    AttackType(Severity severity) {
        this.severity = severity;
    }

}


