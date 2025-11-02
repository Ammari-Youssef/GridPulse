package com.youssef.GridPulse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Controller
public class ApiResolver {

    private final UptimeTracker uptimeTracker;

    @QueryMapping
    public String hello() {
        return "Hello, GraphQL! API is running.";
    }

    @QueryMapping
    public String version() {
        return "GridPulse v1.0.0";
    }

    @QueryMapping
    public String uptime() {
        return uptimeTracker.getElapsedTime(Instant.now());
    }

    /**
     * Consider changing this simple minimal footprint by adding Actuator dependency in future.
     */
    @Component
    static class UptimeTracker {
        private final Instant startTime = Instant.now();

        public String getElapsedTime(Instant currentTime) {
            Duration uptime = Duration.between(startTime, currentTime);
            long days = uptime.toDays();
            long hours = uptime.toHours() % 24;
            long minutes = uptime.toMinutes() % 60;

            return String.format("⏱ Uptime: %d days, %d hours, %d minutes", days, hours, minutes);
        }
    }
}
