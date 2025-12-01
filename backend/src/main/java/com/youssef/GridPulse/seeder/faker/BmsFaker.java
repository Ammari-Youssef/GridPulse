package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.enums.BatteryChemistry;
import com.youssef.GridPulse.domain.bms.enums.BatteryHealthStatus;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class BmsFaker {

    private final Faker faker = new Faker();

    public Bms generate() {
        return Bms.builder()

                .name("Battery Pack " + faker.number().numberBetween(1, 50))
                .model("BMS-" + faker.number().digits(3))
                .manufacturer(faker.company().name())
                .version("v" + faker.number().numberBetween(1, 3) + "." + faker.number().numberBetween(0, 9))

                // --- Battery metrics ---
                .soc((float) faker.number().randomDouble(1, 20, 100)) // State of Charge %
                .soh(faker.options().option(BatteryHealthStatus.class)) // Enum random
                .batteryChemistry(faker.options().option(BatteryChemistry.class)) // Enum random
                .cycles(faker.number().numberBetween(50, 2000)) // realistic cycle count
                .temperature((float) faker.number().randomDouble(1, 20, 45)) // °C
                .voltage((float) faker.number().randomDouble(1, 300, 800)) // V

                // --- Audit fields ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
