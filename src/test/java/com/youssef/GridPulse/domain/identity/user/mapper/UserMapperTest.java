package com.youssef.GridPulse.domain.identity.user.mapper;

import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.youssef.GridPulse.domain.identity.user.Role.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneId;


@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper mapper;

    // Counter
    private static int testCounter = 1;

    @BeforeAll
    static void start() {
        System.out.println("--------------------------- UserMapperTest start ---------------------------------------\n");
    }

    @AfterAll
    static void finish() {
        System.out.println("\n ------------------------------ UserMapperTest end ---------------------------------------");
    }

    @BeforeEach
    void setUp() {
        System.out.println("******************************* UserMapper Test No." + testCounter + " SETUP **********************************\n");
        mapper = new UserMapperImpl();


    }

    @AfterEach
    void tearDown() {
        System.out.println("******************************* UserMapper Test No." + testCounter + " TEARDOWN **********************************\n");
        mapper = null;
        testCounter++;
    }

    @Test
    void should_map_toHistory() {

        System.out.println("--------------------------- should_map_toHistory ---------------------------------------\n");
        // Given

        User user = User.builder()
                .email("email@email.com")
                .firstname("firstName")
                .lastname("lastName")
                .role(USER)
                .password("password")
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();
        // When
        UserHistory history = mapper.toHistory(user);

        // Then
        assertThat(history.getOriginalId()).isEqualTo(user.getId());
        assertThat(history.getCreatedBy()).isEqualTo(user.getCreatedBy());
        assertThat(history.getEmail()).isEqualTo(user.getEmail());
        assertThat(history.getFirstname()).isEqualTo(user.getFirstname());
        assertThat(history.getLastname()).isEqualTo(user.getLastname());
        assertThat(history.getRole()).isEqualTo(user.getRole().toString());
        assertThat(history.isEnabled()).isEqualTo(user.isEnabled());

    }

    @Test
    void should_map_toHistory_when_user_isNull() {

        System.out.println("--------------------------- should_map_toHistory_when_user_isNull ---------------------------------------\n");
        // Given
        User user = null;

        // When
        UserHistory history = mapper.toHistory(user);

        // Then
        assertThat(history).isNull(); // The mapper should return null, not throw an exception


    }

    @Test
    void should_map_registerInput_toEntity() {

        System.out.println("--------------------------- should_map_toEntity ---------------------------------------\n");

        // Given
        RegisterInput registerInput = RegisterInput.builder()
                .email("email@email.com")
                .firstname("John")
                .lastname("Doe")
                .password("password")
                .build();
        // When
        User user = mapper.toEntity(registerInput);

        // Then
        assertThat(user.getEmail()).isEqualTo("email@email.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFirstname()).isEqualTo("John");
        assertThat(user.getLastname()).isEqualTo("Doe");

        // Assert that constants are applied
        assertThat(user.getRole()).isEqualTo(USER); // From @Mapping constant
        assertThat(user.getSource()).isEqualTo("app"); // From @Mapping constant

        // Assert that ignored fields are null & not set by the mapper
        assertThat(user.getId()).isNull();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
        assertThat(user.getCreatedBy()).isNotNull(); // Set by the audit listener

    }

    @Test
    void toEntity_FromNullInput_ShouldReturnNull() {
        System.out.println("--------------------------- toEntity_FromNullInput_ShouldReturnNull ---------------------------------------\n");

        // Act
        User user = mapper.toEntity(null);

        // Assert
        assertThat(user).isNull();
    }

    @Test
    void toEntity_FromInputWithNullFields_ShouldMapNonNullFields() {

        System.out.println("--------------------------- toEntity_FromInputWithNullFields_ShouldMapNonNullFields ---------------------------------------\n");

        // Given
        RegisterInput input = new RegisterInput();
        input.setEmail("partial@example.com");
        // firstName and lastName are intentionally left null

        // When
        User user = mapper.toEntity(input);

        // Then
        assertThat(user.getEmail()).isEqualTo("partial@example.com");
        assertThat(user.getFirstname()).isNull(); // Should be null, not throw an exception
        assertThat(user.getLastname()).isNull();  // Should be null, not throw an exception

        // Constants should still be applied
        assertThat(user.getRole()).isEqualTo(USER);
    }
}