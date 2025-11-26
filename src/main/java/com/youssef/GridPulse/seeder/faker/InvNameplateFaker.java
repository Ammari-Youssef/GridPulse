package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.enums.DerType;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class InvNameplateFaker {

    private final Faker faker = new Faker();

    public InvNameplate generate(Inverter inverter) {
        return InvNameplate.builder()

                // SunSpec model fields
                .modelId(120) // fixed for InvNameplate
                .modelLength(faker.number().numberBetween(1, 10))
                .inverter(inverter)

                // DER type (e.g. PV = 1, PV+Storage = 2)
                .derType(faker.options().option(DerType.class))

                // Power ratings
                .wRating(faker.number().numberBetween(10000, 100000)) // W
                .wRatingSf(1)
                .vaRating(faker.number().numberBetween(12000, 110000)) // VA
                .vaRatingSf(1)

                // VAR ratings per quadrant
                .varRatingQ1(faker.number().numberBetween(5000, 30000))
                .varRatingQ2(faker.number().numberBetween(5000, 30000))
                .varRatingQ3(faker.number().numberBetween(5000, 30000))
                .varRatingQ4(faker.number().numberBetween(5000, 30000))
                .varRatingSf(1)

                // AC current rating
                .acCurrentRating(faker.number().numberBetween(50, 200)) // A
                .acCurrentRatingSf(1)

                // Power factor ratings
                .pfRatingQ1(faker.number().numberBetween(90, 100))
                .pfRatingQ2(faker.number().numberBetween(90, 100))
                .pfRatingQ3(faker.number().numberBetween(90, 100))
                .pfRatingQ4(faker.number().numberBetween(90, 100))
                .pfRatingSf(1)

                // Energy & capacity
                .energyRatingWh(faker.number().numberBetween(500000, 2000000)) // Wh
                .energyRatingWhSf(1)
                .ampHourRating(faker.number().numberBetween(500, 5000)) // AH
                .ampHourRatingSf(1)

                // Charge/discharge rates
                .maxChargeRate(faker.number().numberBetween(5000, 50000)) // W
                .maxChargeRateSf(1)
                .maxDischargeRate(faker.number().numberBetween(5000, 60000)) // W
                .maxDischargeRateSf(1)

                // Pad
                .pad(0)

                // Audit fields
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}