package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class InverterFaker {

    private final Faker faker = new Faker();

    public Inverter generate() {

        return Inverter.builder()

                .name("Inverter " + faker.number().numberBetween(1, 50))
                .model("INV-" + faker.number().digits(3))
                .version("v" + faker.app().version())
                .manufacturer(faker.company().name())

                // --- Audit fields from BaseEntity ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
