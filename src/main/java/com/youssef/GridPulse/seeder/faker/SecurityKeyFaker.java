package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.enums.KeySource;
import com.youssef.GridPulse.domain.security.enums.KeyStatus;
import com.youssef.GridPulse.domain.security.enums.SecurityType;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;


@Component
public class SecurityKeyFaker {

    private final Faker faker = new Faker();

    public SecurityKey generate(Device device, User owner) {
        return SecurityKey.builder()

                .name("Key-" + faker.number().digits(4))

                // --- Relationships ---
                .device(device)
                .owner(owner)

                // --- Key metadata ---
                .securityType(faker.options().option(SecurityType.class))
                .serialNumber("SEC-" + faker.number().digits(6))

                // --- Fake key material ---
                .publicKey("-----BEGIN PUBLIC KEY-----\n" +
                        faker.random().hex(64) + "\n-----END PUBLIC KEY-----")
                .privateKey("-----BEGIN PRIVATE KEY-----\n" +
                        faker.random().hex(128) + "\n-----END PRIVATE KEY-----")

                // --- Source & status ---
                .keySource(faker.options().option(KeySource.class))
                .status(faker.options().option(KeyStatus.class))

                // --- Optional revoked timestamp ---
                .revokedTimestamp(faker.bool().bool()
                        ? OffsetDateTime.now().minusDays(faker.number().numberBetween(1, 365))
                        : null)

                // --- Audit fields ---
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
