package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class InvCommonFaker {

    private final Faker faker = new Faker();

    public InvCommon generate(Inverter inverter) {
        return InvCommon.builder()

                // SunSpec model fields
                .modelId(1) // fixed for InvCommon
                .modelLength(faker.number().numberBetween(1, 10))
                .inverter(inverter)

                // Manufacturer metadata
                .manufacturer(faker.company().name())
                .model("INV-" + faker.number().digits(3))
                .options(faker.options().option("WiFi Monitoring", "Remote Control", "Smart Grid Ready"))
                .version("v" + faker.app().version())
                .serialNumber("SN-" + faker.number().digits(6))

                // Modbus fields
                .deviceAddress(faker.number().numberBetween(1, 255))
                .pad(0)

                // Audit fields from BaseEntity
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
