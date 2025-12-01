package com.youssef.GridPulse.seeder.config;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.seeder.util.ResourcePool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SeedingProperties.class})
@RequiredArgsConstructor
@Slf4j
public class SeedingConfig {

    private SeedingProperties seedingProperties;

    @Bean("bmsPool")
    public ResourcePool<Bms> bmsPool() {
        log.info("Creating bmsPool bean");
        return new ResourcePool<>();
    }

    @Bean("meterPool")
    public ResourcePool<Meter> meterPool() {
        log.info("Creating Meter bean");
        return new ResourcePool<>();
    }
}
