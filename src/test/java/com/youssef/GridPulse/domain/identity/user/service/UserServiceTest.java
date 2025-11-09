package com.youssef.GridPulse.domain.identity.user.service;

import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.domain.identity.user.dto.UpdateUserInput;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Test Suite")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserHistoryRepository userHistoryRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<UserHistory> historyCaptor;

    private User testUser;
    private UUID testUserId;

    private static Instant suiteStartTime;
    private static int testCounter = 1;

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        System.out.println("\n⭐ UserService Test Execution Started");
        System.out.println("⏰ Start Time: " + suiteStartTime);
        System.out.println("-".repeat(50));
    }

    @AfterAll
    static void endTestExecution() {
        Instant suiteEndTime = Instant.now();
        long duration = java.time.Duration.between(suiteStartTime, suiteEndTime).toMillis();

        System.out.println("-".repeat(50));
        System.out.println("🏁 UserService Test Execution Completed");
        System.out.println("⏰ End Time: " + suiteEndTime);
        System.out.println("⏱️  Total Duration: " + duration + "ms");
        System.out.println("=".repeat(50));
    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Test " + testCounter + " - Completed");
        testCounter+=1;
    }

    @BeforeEach
    void setUp() {
        System.out.println("📋 Test " + testCounter + " - Setting up...");

        testUserId = UUID.randomUUID();
        testUser = User.builder()
                .id(testUserId)
                .createdAt(OffsetDateTime.now(ZoneId.of("Africa/Casablanca")))
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .build();
    }

    // ========== GET ALL USERS ==========

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // given
        User user2 = User.builder().id(UUID.randomUUID()).firstname("Jane").build();
        when(userRepository.findAll()).thenReturn(List.of(testUser, user2));

        // when
        List<User> result = userService.getAllUsers();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(testUser, user2);
        verify(userRepository).findAll();
    }

    // ========== GET USERS ACTIVITY HISTORY ==========

    @Test
    void getUsersActivityHistory_ShouldReturnAllHistory() {
        // given
        UserHistory history1 = UserHistory.builder().id(UUID.randomUUID()).build();
        UserHistory history2 = UserHistory.builder().id(UUID.randomUUID()).build();
        when(userHistoryRepository.findAll()).thenReturn(List.of(history1, history2));

        // when
        List<UserHistory> result = userService.getUsersActivityHistory();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(history1, history2);
        verify(userHistoryRepository).findAll();
    }

    // ========== GET USER BY ID ==========

    @Test
    void getUserById_WhenUserExists_ReturnsUser() {
        // given
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        // when
        Optional<User> result = userService.getUserById(testUserId);

        // then
        assertThat(result).isPresent().contains(testUser);
        verify(userRepository).findById(testUserId); // Verify it was retrieved once
    }

    @Test
    void getUserById_WhenUserNotFound_ReturnsEmpty() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when
        Optional<User> result = userService.getUserById(nonExistentId);

        // then
        assertThat(result).isEmpty();
        verify(userRepository).findById(nonExistentId);
    }

    // ========== UPDATE USER ==========

    @Test
    void updateUser_WhenUserExists_ShouldUpdateAndSaveHistory() {
        // given
        UpdateUserInput input = new UpdateUserInput("Jane", "Smith");
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // when
        User result = userService.updateUser(testUserId, input);

        // then
        assertThat(result).isEqualTo(testUser);

        // Verify history was saved with updatedRecord = true
        verify(userHistoryRepository).save(historyCaptor.capture());
        UserHistory savedHistory = historyCaptor.getValue();
        assertThat(savedHistory.isUpdatedRecord()).isTrue();

        // Verify user was saved with updated fields
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getFirstname()).isEqualTo("Jane");
        assertThat(savedUser.getLastname()).isEqualTo("Smith");

        verify(userRepository).findById(testUserId);
        verify(userMapper).toHistory(testUser);
    }

    @Test
    void updateUser_WhenUserNotFound_ShouldThrowException() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        UpdateUserInput input = new UpdateUserInput("Jane", "Smith");
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUser(nonExistentId, input));
        assertThat(exception.getMessage()).contains("User not found");

        verify(userRepository).findById(nonExistentId);
        verify(userHistoryRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_WithPartialFields_ShouldUpdateOnlyProvidedFields() {
        // given
        UpdateUserInput input = new UpdateUserInput(null, "Smith"); // Only update lastname
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // when
        User result = userService.updateUser(testUserId, input);

        // then
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        // Only lastname should be changed, firstname should remain unchanged
        assertThat(savedUser.getFirstname()).isEqualTo("John"); // Original value
        assertThat(savedUser.getLastname()).isEqualTo("Smith"); // New value
        assertThat(result).isEqualTo(testUser);
    }

    // ========== DELETE USER BY ID ==========

    @Test
    void deleteUserById_WhenUserExists_ShouldDeleteAndSaveHistory() {
        // given
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        doNothing().when(userRepository).deleteById(testUserId);

        // when
        boolean result = userService.deleteUserById(testUserId);

        // then
        assertThat(result).isTrue();

        // Verify history was saved with deletedRecord = true
        verify(userHistoryRepository).save(historyCaptor.capture());
        UserHistory savedHistory = historyCaptor.getValue();
        assertThat(savedHistory.isDeletedRecord()).isTrue();

        verify(userRepository).findById(testUserId);
        verify(userMapper).toHistory(testUser);
        verify(userRepository).deleteById(testUserId);
    }

    @Test
    void deleteUserById_WhenUserNotFound_ShouldReturnFalse() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when
        boolean result = userService.deleteUserById(nonExistentId);

        // then
        assertThat(result).isFalse();
        verify(userRepository).findById(nonExistentId);
        verify(userHistoryRepository, never()).save(any());
        verify(userRepository, never()).deleteById(any());
    }

    // ========== TOGGLE USER ENABLE STATUS ==========

    @Test
    void toggleUserEnableStatus_DisableUser_ShouldSaveHistoryWithPreviousState() {
        // given - user is initially ENABLED
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // when - toggle from ENABLED to DISABLED
        boolean result = userService.toggleUserEnableStatus(testUserId);

        // then
        assertThat(result).isTrue();

        verify(userHistoryRepository).save(historyCaptor.capture());
        UserHistory savedHistory = historyCaptor.getValue();

        // The history should record the state BEFORE the toggle
        assertThat(savedHistory.isEnabled()).isTrue(); // User was enabled before the action

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.isEnabled()).isFalse(); // Now user is disabled after the action
    }

    @Test
    void toggleUserEnableStatus_EnableUser_ShouldSaveHistoryWithPreviousState() {
        // given - user is initially DISABLED
        testUser.setEnabled(false);
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // when - toggle from DISABLED to ENABLED
        boolean result = userService.toggleUserEnableStatus(testUserId);

        // then
        assertThat(result).isTrue();

        verify(userHistoryRepository).save(historyCaptor.capture());
        UserHistory savedHistory = historyCaptor.getValue();

        // The history should record the state BEFORE the toggle
        assertThat(savedHistory.isEnabled()).isFalse(); // User was disabled before the action

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.isEnabled()).isTrue(); // Now user is enabled after the action
    }

    @Test
    void toggleUserEnableStatus_WhenUserNotFound_ShouldReturnFalse() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when
        boolean result = userService.toggleUserEnableStatus(nonExistentId);

        // then
        assertThat(result).isFalse();
        verify(userRepository).findById(nonExistentId);
        verify(userHistoryRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void toggleUserEnableStatus_EnableUser_ShouldSaveCorrectHistory() {
        // given
        testUser.setEnabled(false); // Start with disabled user
        UserHistory history = UserHistory.builder().id(UUID.randomUUID()).build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toHistory(testUser)).thenReturn(history);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // when - toggle to enabled
        boolean result = userService.toggleUserEnableStatus(testUserId);

        // then
        assertThat(result).isTrue();

        // Verify history was saved with disabledRecord = false (since we're enabling)
        verify(userHistoryRepository).save(historyCaptor.capture());
        UserHistory savedHistory = historyCaptor.getValue();
        assertThat(savedHistory.isEnabled()).isFalse();
        assertThat(savedHistory).isEqualTo(history);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.isEnabled()).isTrue(); // Should be enabled now
    }

    // ========== MARK HISTORY RECORD AS SYNCED ==========

    @Test
    void markHistoryRecordAsSynced_WhenRecordExists_ShouldReturnTrue() {
        // given
        UUID historyId = UUID.randomUUID();
        when(userHistoryRepository.markAsSynced(historyId)).thenReturn(1);

        // when
        boolean result = userService.markHistoryRecordAsSynced(historyId);

        // then
        assertThat(result).isTrue();
        verify(userHistoryRepository).markAsSynced(historyId);
    }

    @Test
    void markHistoryRecordAsSynced_WhenRecordNotFound_ShouldThrowException() {
        // given
        UUID nonExistentHistoryId = UUID.randomUUID();
        when(userHistoryRepository.markAsSynced(nonExistentHistoryId)).thenReturn(0);

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.markHistoryRecordAsSynced(nonExistentHistoryId));
        assertThat(exception.getMessage()).contains("History record not found");

        verify(userHistoryRepository).markAsSynced(nonExistentHistoryId);
    }
}