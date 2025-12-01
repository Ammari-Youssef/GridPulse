package com.youssef.GridPulse.seeder.faker;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.enums.Severity;
import com.youssef.GridPulse.domain.message.payload.*;
import com.youssef.GridPulse.domain.message.payload.enums.*;
import com.youssef.GridPulse.domain.message.util.SeverityInterpreter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

@Component
public class MessageFaker {

    private final Faker faker = new Faker();

    public Message generate(Device device) {
        MessageType type = faker.options().option(MessageType.class);
        Object payload;

        switch (type) {
            case BMS -> payload = new BmsPayload(
                    faker.number().numberBetween(1, 1000),
                    (float) faker.number().randomDouble(2, 300, 800),   // voltage
                    (float)  faker.number().randomDouble(1, 20, 100),    // SOC %
                    (int) faker.number().randomNumber(3),     // cycles
                    (float)faker.number().randomDouble(1, 10, 100),    // SOH %
                    (float) faker.number().randomDouble(1, 20, 45)      // temperature
            );

            case IDS -> payload = new IdsPayload(
                    faker.options().option(AttackType.class),
                    faker.internet().ipV4Address(),
                    faker.internet().ipV4Address(),
                    faker.number().numberBetween(1024, 65535),
                    faker.number().numberBetween(1024, 65535),
                    faker.options().option("TCP", "UDP", "ICMP"),
                    faker.lorem().sentence()
            );

            case INVERTER -> payload = new InverterPayload(
                    (float)  faker.number().randomDouble(2, 5000, 50000), // out_pwr
                    (float)  faker.number().randomDouble(2, 180, 260),    // voltage
                    (float)  faker.number().randomDouble(2, 10, 200),     // current
                    (float) faker.number().randomDouble(2, 49, 61),      // frequency Hz
                    (float) faker.number().randomDouble(2, 85, 99),      // efficiency %
                    (float) faker.number().randomDouble(2, 20, 60)       // temperature °C
            );

            case METER -> payload = new MeterPayload(
                    (float)   faker.number().randomDouble(2, 1000, 10000), // pwrIn
                    (float)  faker.number().randomDouble(2, 1000, 10000)  // pwrOut
            );

            case SOFTWARE -> payload = new SoftwarePayload(
                    OffsetDateTime.now().minusMinutes(faker.number().numberBetween(1, 60)),
                    OffsetDateTime.now(),
                    faker.options().option(SoftwarePackageType.class),
                    faker.app().version(),
                    faker.options().option(SoftwareMessageUpdateStatus.class)
            );

            case SYSTEM -> payload = new SystemPayload(
                    faker.number().numberBetween(10, 100), // cpuUsage %
                    faker.number().numberBetween(10, 100), // memoryUsage %
                    faker.number().numberBetween(10, 100), // diskUsage %
                    (float)faker.number().randomDouble(2, 20, 60), // temperature °C
                    Double.parseDouble(faker.address().latitude()),
                    Double.parseDouble(faker.address().longitude())
            );

            case HEARTBEAT -> payload = new HeartbeatPayload(
                    faker.number().numberBetween(1, 100000) // uptime in seconds
            );

            default -> payload = Map.of("msg", "Unknown type");
        }

        // Calculate severity using your interpreter
        Severity severity = SeverityInterpreter.calculate(type, payload);
        String explanation = type == MessageType.IDS ?  SeverityInterpreter.explain(type, faker.options().option(AttackType.class)): SeverityInterpreter.explain(severity, type);

        return Message.builder()

                .device(device)
                .cloudMessageId("MSG-" + faker.number().digits(6))
                .messageText(payload) // Hibernate will persist as JSONB
                .receivedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).minusSeconds(faker.number().numberBetween(1, 60)))
                .sentAt(OffsetDateTime.now())
                .severity(severity)
                .messageType(type)
                .status(faker.options().option(MessageStatus.class))
                .format(faker.options().option(MessageFormat.class))
                .priority(faker.options().option(MessagePriority.class))
                .explanation(explanation)

                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .createdBy("SYSTEM")
                .source(Source.APP)
                .build();
    }
}
