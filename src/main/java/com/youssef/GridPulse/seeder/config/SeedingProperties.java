package com.youssef.GridPulse.seeder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.seed")
public class SeedingProperties {
    private boolean enabled = false;

    private int users = 10;
    private int operators = 5;
    private int fleets = 5;

    private int bms = 200;
    private int meter = 200;

    private int devicesPerUser = 20;

    private int invertersPerDevice = 2;
    private int totalInverters = 50; // optional global pool

    private int securityKeysPerDevice = 1;
    private int messagesPerDevice = 3;
}