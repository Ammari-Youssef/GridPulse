package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.enums.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class InvSettingsFaker {

    private final Faker faker = new Faker();

    public InvSettings generate(Inverter inverter) {
        return InvSettings.builder()

                // SunSpec model fields
                .modelId(121) // fixed for InvSettings
                .modelLength(faker.number().numberBetween(1, 10))
                .inverter(inverter)

                // --- Power & Voltage ---
                .wMax(faker.number().numberBetween(10000, 100000)) // W
                .vRef(faker.number().numberBetween(380, 420))      // V
                .vRefOfs(faker.number().numberBetween(0, 10))      // V offset
                .vMax(faker.number().numberBetween(700, 1000))     // V
                .vMin(faker.number().numberBetween(300, 350))      // V
                .vaMax(faker.number().numberBetween(12000, 110000))// VA

                // --- Reactive power limits ---
                .varMaxQ1(faker.number().numberBetween(5000, 30000))
                .varMaxQ2(faker.number().numberBetween(5000, 30000))
                .varMaxQ3(faker.number().numberBetween(5000, 30000))
                .varMaxQ4(faker.number().numberBetween(5000, 30000))

                // --- Ramp & gradient ---
                .wGra(faker.number().numberBetween(10, 200)) // % WMax/sec

                // --- Power factor minimums ---
                .pfMinQ1(faker.number().numberBetween(85, 95))
                .pfMinQ2(faker.number().numberBetween(85, 95))
                .pfMinQ3(faker.number().numberBetween(85, 95))
                .pfMinQ4(faker.number().numberBetween(85, 95))

                // --- Enums ---
                .varAct(faker.options().option(VarAction.class))
                .clcTotVa(faker.options().option(ClcTotVaMethod.class))
                .connPh(faker.options().option(ConnPhase.class))

                // --- Other operational settings ---
                .maxRmpRte(faker.number().numberBetween(5, 20)) // %
                .ecpNomHz(faker.options().option(50, 60))       // Hz

                // --- Audit fields ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}

