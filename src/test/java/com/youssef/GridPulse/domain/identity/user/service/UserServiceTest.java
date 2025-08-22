package com.youssef.GridPulse.domain.identity.user.service;

import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock  private UserRepository userRepository;
    @Mock  private UserHistoryRepository userHistoryRepository;

    @Mock  private UserMapper userMapper;

    private UserService userService;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        // 1. Initialize the mocks and get the cleanup resource
        autoCloseable = MockitoAnnotations.openMocks(this);
        // 2. Since @InjectMocks doesn't work as well with JUnit 4,
        // we often create the service manually and inject the mock ourselves.
        userService = new UserService(userRepository, userHistoryRepository, userMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        // 3. CRUCIAL: Close the resource to trigger cleanup!
        // This checks for unused stubs and other validation.
        autoCloseable.close();
    }

    @Test
    void getAllUsers() {
        userService.getAllUsers();

        verify(userRepository).findAll();
    }

    @Test
    void getUsersActivityHistory() {
        // when
        userService.getUsersActivityHistory();
        // then
        verify(userHistoryRepository).findAll();
    }

    @Test
    void getUserById() {
        // given
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .createdAt(Instant.now())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .build();

        // when
        when(userRepository.save(any(User.class))).thenAnswer(invk -> {
            User u = invk.getArgument(0);
            u.setId(id);
            return u;
        });
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
    }
}