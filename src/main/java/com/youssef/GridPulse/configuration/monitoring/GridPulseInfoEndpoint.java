package com.youssef.GridPulse.configuration.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.Map;

@Component
@Endpoint(id = "gridpulseInfo")
public class GridPulseInfoEndpoint {

    private static final String VERSION = "v1.0.0";

    @ReadOperation
    public Map<String, Object> gridpulseStatus() {
        return Map.of(
                "project", "GridPulse",
                "status", "UP",
                "version", VERSION,
                "uptime", ManagementFactory.getRuntimeMXBean().getUptime() + " ms"
        );
    }
}
