package com.youssef.GridPulse.utils;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@UtilityClass
public class TestSuiteUtils {
    public static final UUID testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static final UUID testUserId2 = UUID.fromString("012e4567-e89b-12d3-a456-426614174000");

    public User createTestUser(User user){
        return User.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .source(user.getSource())
                .build();
    }

    public UserHistory createTestUserHistory(UserHistory userHistory){
        return UserHistory.builder()
                .id(userHistory.getId())
                .originalId(userHistory.getOriginalId())
                .firstname(userHistory.getFirstname())
                .lastname(userHistory.getLastname())
                .email(userHistory.getEmail())
                .password(userHistory.getPassword())
                .createdBy(userHistory.getCreatedBy())
                .role(userHistory.getRole())
                .enabled(userHistory.isEnabled())
                .createdAt(userHistory.getCreatedAt())
                .source(userHistory.getSource())
                .build();
    }

    public User createTestUserA() {
        return User.builder()
                .id(testUserId)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encoded password")
                .role(Role.USER)
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .updatedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(5))
                .source(Source.APP)
                .build();
    }

    public User createTestUserB() {
        return User.builder()
                .id(testUserId2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .role(Role.ADMIN)
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .updatedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(6))
                .source(Source.APP)
                .build();
    }

    public UserHistory createTestUserHistoryA() {
        return UserHistory.builder()
                .id(UUID.randomUUID())
                .originalId(testUserId)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encoded password")
                .createdBy("SYSTEM")
                .role("ADMIN")
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(5))
                .source(Source.APP)
                .build();
    }

    public UserHistory createTestUserHistoryB() {
        return  UserHistory.builder()
                .id(UUID.randomUUID())
                .originalId(testUserId2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .role("USER")
                .createdBy(testUserId.toString())
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(6))
                .source(Source.APP)
                .build();
    }
}
