package com.youssef.GridPulse.configuration.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MqttConfig {

    @Bean
    @Profile("dev")
    public StubMqttClient stubMqttClient() {
        return new StubMqttClient(true); // always connected in dev
    }

    @Bean
    @Profile("prod")
    public StubMqttClient realMqttClient() {
        // TODO: integrate actual MQTT client later
        return new StubMqttClient(false);
    }
}
