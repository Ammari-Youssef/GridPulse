package com.youssef.GridPulse.configuration.monitoring;

import com.youssef.GridPulse.configuration.mqtt.StubMqttClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class GridPulseHealthIndicator implements HealthIndicator {

    private final StubMqttClient mqttClient;

    public GridPulseHealthIndicator() {
        // For now, stubbed client - I replace with real one later
        this.mqttClient = new StubMqttClient(true);
    }

    @Override
    public Health health() {
        boolean mqttConnected = mqttClient.isConnected();

        Health.Builder status = mqttConnected ? Health.up() : Health.down();

        return status
                .withDetail("component", "MQTT broker")
                .withDetail("connected", mqttConnected)
                .withDetail("version", "1.0.0")
                .withDetail("environment", System.getProperty("spring.profiles.active", "unknown"))
                .build();
    }
}