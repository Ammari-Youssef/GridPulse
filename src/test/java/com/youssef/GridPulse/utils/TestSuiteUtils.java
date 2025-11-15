package com.youssef.GridPulse.utils;

import com.youssef.GridPulse.common.base.Source;
import com.youssef.GridPulse.domain.identity.auth.dto.AuthenticationResponse;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.token.Token;
import com.youssef.GridPulse.domain.identity.token.TokenType;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@UtilityClass
public class TestSuiteUtils {
    // --- Common UUIDs for user tests ---
    public static final UUID TEST_USER_ID_1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static final UUID TEST_USER_ID_2 = UUID.fromString("012e4567-e89b-12d3-a456-426614174000");

    // --- Common UUIDs for inverter tests ---
    public static final UUID TEST_INVERTER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID TEST_INVERTER_ID2 = UUID.fromString("12121212-1212-1212-1212-121212121212");
    public static final UUID TEST_INVERTER_HISTORY_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID TEST_INVERTER_HISTORY_ID2 = UUID.fromString("21212121-2121-2121-2121-212121212121");

    public AuthenticationResponse createAuthenticationResponse() {
        return AuthenticationResponse.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .build();
    }

    public RegisterInput createRegisterInput() {
        return RegisterInput.builder()
                .email("jane.smith@example.com")
                .password("plainPassword")
                .firstname("Jane")
                .lastname("Smith")
                .build();
    }

    public RegisterInput createRegisterInput(String email, String password, String firstname, String lastname) {
        return RegisterInput.builder()
                .email(email)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }

    public User createUser(UUID id, String email, String password, String firstname, String lastname, Role role) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .role(role)
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public UserHistory createUserHistoryFromUser(User user, boolean createdRecord) {
        return UserHistory.builder()
                .originalId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .createdRecord(createdRecord)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .source(Source.APP)
                .build();
    }

    public User createTestUser(User user) {
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

    public UserHistory createTestUserHistory(UserHistory userHistory) {
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
                .id(TEST_USER_ID_1)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .updatedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(5))
                .source(Source.APP)
                .build();
    }

    public User createTestUserB() {
        return User.builder()
                .id(TEST_USER_ID_2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .password("encodedPassword")
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
                .originalId(TEST_USER_ID_1)
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
        return UserHistory.builder()
                .id(UUID.randomUUID())
                .originalId(TEST_USER_ID_2)
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .password("encoded password")
                .role("USER")
                .createdBy(TEST_USER_ID_1.toString())
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")).plusMonths(6))
                .source(Source.APP)
                .build();
    }

    public static User createTestAuthUserA() {
        return User.builder()
                .id(TEST_USER_ID_1)
                .email("test@example.com")
                .password("encodedPassword")
                .firstname("John")
                .lastname("Doe")
                .role(Role.USER)
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public static Inverter createTestInverter(Inverter entity) {
        return Inverter.builder()
                .id(entity.getId())
                .name(entity.getName())
                .model(entity.getModel())
                .version(entity.getVersion())
                .manufacturer(entity.getManufacturer())
                .source(entity.getSource())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static InverterInput createTestInverterInput() {
        return new InverterInput("Test Inverter", "TX5000", "v2.1", "Test Manufacturer");
    }

    public static InverterInput createTestInverterInput(InverterInput input) {
        return new InverterInput(input.name(), input.model(), input.version(), input.manufacturer());
    }

    public static Inverter createTestInverterA() {
        return Inverter.builder()
                .id(TEST_INVERTER_ID)
                .name("Test Inverter")
                .model("Test Model")
                .version("Test Version")
                .manufacturer("Test Manufacturer")
                .source(Source.APP)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .updatedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public static Inverter createTestInverterB() {
        return Inverter.builder()
                .id(TEST_INVERTER_ID2)
                .name("Test Inverter 2")
                .model("Test Model")
                .version("Test Version")
                .manufacturer("SolarEclipse")
                .source(Source.APP)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .updatedAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public static InverterHistory createTestInverterHistoryA() {
        return InverterHistory.builder()
                .id(TEST_INVERTER_HISTORY_ID)
                .originalId(TEST_INVERTER_ID)
                .name("Test Inverter")
                .model("Test Model")
                .version("Test Version")
                .manufacturer("Test Manufacturer")
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public static InverterHistory createTestInverterHistoryB() {
        return InverterHistory.builder()
                .id(TEST_INVERTER_HISTORY_ID2)
                .originalId(TEST_INVERTER_ID2)
                .name("Test Inverter")
                .model("Test Model")
                .version("Test Version")
                .manufacturer("Test Manufacturer")
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
    }

    public Token createBearerToken(User user, String tokenValue) {
        return Token.builder()
                .user(user)
                .token(tokenValue)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

    public Token createRefreshToken(User user, String tokenValue) {
        return Token.builder()
                .user(user)
                .token(tokenValue)
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .build();
    }
}
