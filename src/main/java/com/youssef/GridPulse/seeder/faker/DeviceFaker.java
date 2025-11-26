package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class DeviceFaker {

    private final Faker faker = new Faker();

    public Device generate(User user, User operator, Bms bms, Meter meter, Inverter inverter, Fleet fleet) {
        return Device.builder()

                .serialNumber("SN-" + faker.number().digits(6)) // unique serial
                .name(faker.computer().brand())
                .model(faker.device().modelName())
                .manufacturer(faker.device().manufacturer())

                // --- Status & Lifecycle ---
                .status(faker.options().option(DeviceStatus.class))
                .lastSeen(OffsetDateTime.now().minusMinutes(faker.number().numberBetween(1, 120)))

                // --- Software & Connectivity ---
                .softwareVersion(faker.app().version())
                .swUpdateTime(OffsetDateTime.now().minusDays(faker.number().numberBetween(1, 30)))
                .ip(faker.internet().ipV4Address())

                // --- Location ---
                .gpsLat(Double.valueOf(faker.address().latitude()))
                .gpsLong(Double.valueOf(faker.address().longitude()))

                // --- Relationships ---
                .bms(bms)
                .meter(meter)
                .user(user)
                .operator(operator)
                .inverter(inverter)
                .fleet(fleet)

                // --- Audit ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)

                .build();
    }
}