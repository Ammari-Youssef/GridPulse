package com.youssef.GridPulse.domain.base;

import java.time.Duration;
import java.time.Instant;

// Utility class for test logging
public class TestLogger {

    public static void logSuiteStart(Class<?> testClass) {
        System.out.println("\n⭐ " + testClass.getSimpleName() + " Test Execution Started");
        System.out.println("⏰ Start Time: " + Instant.now());
        System.out.println("-".repeat(60));
    }

    public static void logSuiteEnd(Class<?> testClass, Instant startTime) {
        Instant endTime = Instant.now();
        long duration = Duration.between(startTime, endTime).toMillis();

        System.out.println("-".repeat(60));
        System.out.println("🏁 " + testClass.getSimpleName() + " Test Execution Completed");
        System.out.println("⏰ End Time: " + endTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(60));
    }

    public static void logTestStart(int testNumber) {
        System.out.println("📋 Test " + testNumber + " - Setting up...");
    }

    public static void logTestEnd(int testNumber) {
        System.out.println("✅ Test " + testNumber + " - Completed");
    }
}
