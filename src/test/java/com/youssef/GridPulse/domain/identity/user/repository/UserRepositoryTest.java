package com.youssef.GridPulse.domain.identity.user.repository;

import com.youssef.GridPulse.domain.identity.user.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // Test counter
    private static int testCounter = 1;

    @Autowired
    private TestEntityManager entityManager; // Better for setup

    @BeforeAll
    static void start() {
        System.out.println("--------------------------- UserRepositoryTest start ---------------------------------------\n");
    }

    @AfterAll
    static void finish() {
        System.out.println("\n ------------------------------ UserRepositoryTest end ---------------------------------------");
    }

    @BeforeEach
    void setUp() {
        System.out.println("******************************* UserRepository TEST " + testCounter + " SETUP **********************************");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        System.out.println("******************************* UserRepository TEST " + testCounter + " TEARDOWN *************************************");
        testCounter++;
    }

    @Test
    void findByEmail_WhenUserExists_ReturnsUser() {
        // given
        User user = User.builder()
                .email("test@example.com")
                .firstname("John")
                .lastname("Doe")
                .password("encodedPassword")
                .enabled(true)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .build();

        entityManager.persist(user); // Better than repository.save() for setup
        entityManager.flush();

        // when
        Optional<User> result = userRepository.findByEmail("test@example.com");

        // then
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(foundUser -> {
                    assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
                    assertThat(foundUser.getFirstname()).isEqualTo("John");
                    assertThat(foundUser.getLastname()).isEqualTo("Doe");
                });
    }

    @Test
    void findByEmail_WhenUserNotExists_ReturnsEmpty() {
        // when
        Optional<User> result = userRepository.findByEmail("nonexistent@example.com");

        // then
        assertThat(result).isEmpty();
    }
}