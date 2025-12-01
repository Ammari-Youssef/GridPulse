package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class MeterFaker {

    private final Faker faker = new Faker();

    public Meter generate() {
        return Meter.builder()

                .name("Energy Meter " + faker.number().numberBetween(1, 50))
                .model("MTR-" + faker.number().digits(3))
                .manufacturer(faker.company().name())
                .version("v" + faker.number().numberBetween(1, 3) + "." + faker.number().numberBetween(0, 9))

                // --- Meter readings ---
                .powerDispatched((float) faker.number().randomDouble(2, -50, 50)) // kW, can be negative or positive
                .energyConsumed((float) faker.number().randomDouble(2, 100, 10000)) // kWh
                .energyProduced((float) faker.number().randomDouble(2, 50, 5000))   // kWh

                // --- Audit fields ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
