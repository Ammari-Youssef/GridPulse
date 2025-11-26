package com.youssef.GridPulse.seeder;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.seeder.config.SeedingProperties;
import com.youssef.GridPulse.seeder.modules.*;
import com.youssef.GridPulse.seeder.util.ResourcePool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@Profile("seed")
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final SeedingProperties config;

    private final UserSeeder userSeeder;
    private final BmsSeeder bmsSeeder;
    private final MeterSeeder meterSeeder;
    private final FleetSeeder fleetSeeder;
    private final InverterChildrenSeeder inverterChildrenSeeder;
    private final InverterSeeder inverterSeeder;
    private final DeviceSeeder deviceSeeder;
    private final MessageSeeder messageSeeder;
    private final SecurityKeySeeder securityKeySeeder;

    private final ResourcePool<Bms> bmsPool;
    private final ResourcePool<Meter> meterPool;

    @Bean
    @Transactional
    public ApplicationRunner seed() {
        return args -> {

            if (!config.isEnabled()) {
                log.info("[SEED] Disabled");
                return;
            }

            log.info("[SEED] Starting...");

            List<User> users = userSeeder.seed(config.getUsers());
            List<User> operators = userSeeder.seedAdmins(config.getOperators());

            List<Bms> bmsList = bmsSeeder.seed(config.getBms());
            List<Meter> meterList = meterSeeder.seed(config.getMeter());
            List<Fleet> fleets = fleetSeeder.seed(config.getFleets());

            bmsPool.addAll(bmsList);
            meterPool.addAll(meterList);

            List<Inverter> inverters = inverterSeeder.seed(config.getTotalInverters());
            inverters.forEach(inverterChildrenSeeder::seedForInverter);

            Random rnd = new Random();

            for (User user : users) {

                User operator = operators.get(rnd.nextInt(operators.size()));
                Fleet fleet = fleets.get(rnd.nextInt(fleets.size()));

                for (int i = 0; i < config.getDevicesPerUser(); i++) {

                    Bms bms = bmsPool.getOne();
                    Meter meter = meterPool.getOne();
                    Inverter inverter = inverters.get(rnd.nextInt(inverters.size()));

                    Device device = deviceSeeder.seedOne(user, operator, bms, meter, inverter, fleet);

                    securityKeySeeder.seed(user, device, config.getSecurityKeysPerDevice());
                    messageSeeder.seed(device, config.getMessagesPerDevice());
                }
            }

            log.info("[SEED] Completed");
        };
    }
}
