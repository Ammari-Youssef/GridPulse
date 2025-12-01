package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class FleetFaker {

    private final Faker faker = new Faker();

    private final List<Faker> fakers = Arrays.asList(
            new Faker(new Locale("fr")),   // France
            new Faker(new Locale("en-IE")),// Ireland
            new Faker(new Locale("en-GB")),// UK
            new Faker(new Locale("ar")),   // Arabic (Middle East)
            new Faker(new Locale("fr-MA")) // Morocco (French locale often used)
    );

    public Fleet generate() {
        Faker randomFaker = fakers.get(new Random().nextInt(fakers.size()));

        return Fleet.builder()

                .name("Fleet " + randomFaker.number().numberBetween(1, 100))
                .location(randomFaker.address().cityName() + ", " + randomFaker.address().country())
                .owner(randomFaker.company().name())
                .description(randomFaker.lorem().sentence(8))

                // Audit fields from BaseEntity
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }

    public Fleet generate(List<Device> devices) {
        Faker randomFaker = fakers.get(new Random().nextInt(fakers.size()));

        return Fleet.builder()

                .name("Fleet " + randomFaker.number().numberBetween(1, 100))
                .location(randomFaker.address().cityName() + ", " + randomFaker.address().country())
                .owner(randomFaker.company().name())
                .description(randomFaker.lorem().sentence(8))
                .devices(devices)

                // Audit fields from BaseEntity
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }

}
