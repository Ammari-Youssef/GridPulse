package com.youssef.GridPulse.domain.identity.user.service;

import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock  private UserRepository userRepository;
    @Mock  private UserHistoryRepository userHistoryRepository;

    @Mock  private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userHistoryRepository, userMapper);
    }

    @AfterEach
    void tearDown() {

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
    void getUserById_WhenUserExists_ReturnsUserDTO() {
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

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));

        // when
        Optional<User> result = userService.getUserById(id);

        // then
        assertThat(result)
                .isNotNull()
                .isPresent()
                .contains(user)
                .satisfies(dto -> {
                    assertThat(dto.get().getId()).isEqualTo(id);
                    assertThat(dto.get().getFirstname()).isEqualTo("John");
                    assertThat(dto.get().getLastname()).isEqualTo("Doe");
                    assertThat(dto.get().getEmail()).isEqualTo("john.doe@example.com");
                    assertThat(dto.get().isEnabled()).isTrue();
                });

        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_WhenUserNotFound_ThrowsException() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when
        Optional<User> result = userService.getUserById(nonExistentId);

        // then
        assertThat(result).isEmpty();

        verify(userRepository).findById(nonExistentId);
    }
}