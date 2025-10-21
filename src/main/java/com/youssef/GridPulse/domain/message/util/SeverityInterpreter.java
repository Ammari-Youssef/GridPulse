package com.youssef.GridPulse.domain.message.util;

import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.enums.Severity;
import com.youssef.GridPulse.domain.message.payload.enums.AttackType;
import com.youssef.GridPulse.domain.message.payload.enums.SoftwareMessageUpdateStatus;
import com.youssef.GridPulse.domain.message.payload.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SeverityInterpreter {

    public String explain(Severity severity, MessageType type) {
        return switch (type) {

            case SYSTEM -> switch (severity) {
                case CRITICAL -> "CPU overload, Memory overload, disk full";
                case MEDIUM -> "Moderate usage of CPU, Memory, disk";
                case LOW -> "Low usage detected in one or more metrics";
                case INFO -> "All system metrics are within safe thresholds";
            };

            case HEARTBEAT -> switch (severity) {
                case CRITICAL -> "Heartbeat missing or system uptime is critically low";
                case MEDIUM -> "Heartbeat received with moderate uptime";
                case LOW -> "Heartbeat received with low uptime";
                case INFO -> "Heartbeat received with stable uptime";
            };

            case SOFTWARE -> switch (severity) {
                case CRITICAL -> "Software deployment failed";
                case MEDIUM -> "Software deployment encountered moderate issues";
                case LOW -> "Software deployment completed with minor warnings";
                case INFO -> "Software deployment succeeded";
            };

            case BMS -> switch (severity) {
                case CRITICAL -> "Battery State of Health is below 20%";
                case MEDIUM -> "Battery State of Health is between 20% and 30%";
                case LOW -> "Battery State of Health is between 30% and 50%";
                case INFO -> "Battery State of Health is above 50%";
            };

            case METER -> switch (severity) {
                case CRITICAL -> "Power flow anomaly detected: input/output thresholds exceeded";
                case MEDIUM -> "Moderate deviation in power input or output";
                case LOW -> "Minor fluctuation in power readings";
                case INFO -> "Power readings are within normal operating range";
            };

            case INVERTER -> switch (severity) {
                case CRITICAL -> "Voltage exceeds 250V, efficiency below 90%, or temperature above 50ºC";
                case MEDIUM -> "Voltage below 200V, efficiency between 90–95%, or temperature between 40–50ºC";
                case LOW -> "Minor deviation in inverter metrics";
                case INFO -> "Voltage between 200–250V, efficiency between 95–98%, and temperature between 25–40ºC";
            };


            default -> "Severity interpretation not defined for this type";
        };
    }

    /**
     * Provides a detailed explanation for IDS messages based on the specific {@link AttackType}.
     * Only valid when {@code type == MessageType.IDS}.
     *
     * @throws IllegalArgumentException if called with a non-IDS type
     */

    public String explain(MessageType type, AttackType attackType) {
        if (type != MessageType.IDS) {
            throw new IllegalArgumentException("AttackType is only valid for IDS messages");
        }

        return switch (attackType) {
            case BACKDOOR -> "Backdoor detected: unauthorized remote access attempt";
            case EXPLOIT -> "Exploit detected: known vulnerability targeted";
            case DOS_DDOS -> "Denial-of-Service attack detected: traffic flood or resource exhaustion";
            case WORMS -> "Worm activity detected: self-replicating malware spreading across the network";
            case SHELLCODE -> "Shellcode detected: payload attempting to execute arbitrary code";
            case FUZZERS -> "Fuzzer activity detected: input mutation tools probing for vulnerabilities";
            case GENERIC -> "Generic threat detected: pattern matches known suspicious behavior";
            case NORMAL -> "Normal traffic pattern observed";
            case ANALYSIS -> "Analysis tool detected: passive inspection or scanning";
            case RECONNAISSANCE -> "Reconnaissance activity detected: probing for open ports or services";
        };
    }

    public Severity calculate(MessageType type, Object payload) {
        return switch (type) {
            case IDS -> {
                IdsPayload ids = (IdsPayload) payload;
                yield ids.attackType().getSeverity();
            }

            case HEARTBEAT -> {
                HeartbeatPayload hb = (HeartbeatPayload) payload;
                long uptime = hb.uptime();
                yield uptime < 60 ? Severity.CRITICAL :
                        uptime < 3600 ? Severity.MEDIUM :
                                uptime < 86400 ? Severity.LOW : Severity.INFO;
            }

            case SYSTEM -> {
                SystemPayload system = (SystemPayload) payload;
                yield (system.cpuUsage() > 90 || system.memoryUsage() > 90 || system.diskUsage() > 95) ? Severity.CRITICAL :
                        (system.cpuUsage() > 75 || system.memoryUsage() > 75 || system.diskUsage() > 85) ? Severity.MEDIUM :
                                (system.cpuUsage() > 50 || system.memoryUsage() > 50 || system.diskUsage() > 70) ? Severity.LOW : Severity.INFO;
            }

            case SOFTWARE -> {
                SoftwarePayload sw = (SoftwarePayload) payload;
                SoftwareMessageUpdateStatus status = sw.status();

                yield switch (status) {
                    case FAILURE -> Severity.CRITICAL;
                    case SUCCESS -> Severity.INFO;
                };
            }

            case BMS -> {
                BmsPayload bms = (BmsPayload) payload;
                float soh = bms.batterySoh();
                yield soh < 20 ? Severity.CRITICAL :
                        soh < 30 ? Severity.MEDIUM :
                                soh < 50 ? Severity.LOW : Severity.INFO;
            }

            case METER -> {
                MeterPayload meter = (MeterPayload) payload;
                float pwrIn = meter.pwrIn();
                float pwrOut = meter.pwrOut();
                float delta = Math.abs(pwrIn - pwrOut);
                float ratio = pwrOut / (pwrIn == 0 ? 1 : pwrIn); // avoid division by zero

                yield (pwrIn == 0 && pwrOut == 0) ? Severity.CRITICAL :
                        (delta > 500 || ratio < 0.5) ? Severity.CRITICAL :
                                (delta > 250 || ratio < 0.75) ? Severity.MEDIUM :
                                        (delta > 100 || ratio < 0.9) ? Severity.LOW : Severity.INFO;
            }

            case INVERTER -> {
                InverterPayload inv = (InverterPayload) payload;
                yield inv.voltage() > 250 || inv.efficiency() < 90 || inv.temperature() > 50 ? Severity.CRITICAL :
                        inv.voltage() < 200 || inv.efficiency() < 95 || inv.temperature() > 40 ? Severity.MEDIUM :
                                inv.voltage() >= 200 && inv.efficiency() >= 95 && inv.efficiency() <= 98 && inv.temperature() >= 25 ? Severity.INFO : Severity.LOW;
            }


//            default -> Severity.INFO;
        };
    }

}

