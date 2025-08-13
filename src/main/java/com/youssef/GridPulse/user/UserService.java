package com.youssef.GridPulse.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;

    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserHistory> getUsersActivityHistory() {
        return userHistoryRepository.findAll();
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User updateUser(UUID id, UpdateUserInput input) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Save history before updating
        UserHistory history = userMapper.toHistory(existingUser);
        history.setUpdatedRecord(true);
        userHistoryRepository.save(history);

        // Apply other updates
        if (input.firstname() != null) {
            existingUser.setFirstname(input.firstname());
        }
        if (input.lastname() != null) {
            existingUser.setLastname(input.lastname());
        }

        return userRepository.save(existingUser);
    }

    public boolean deleteUserById(UUID id) {
        return userRepository.findById(id).map(user -> {
            // Save history before deletion
            UserHistory historyBeforeDelete = userMapper.toHistory(user);
            historyBeforeDelete.setDeletedRecord(true);
            userHistoryRepository.save(historyBeforeDelete);

            userRepository.deleteById(id);

            return true;
        }).orElse(false); // If user not found
    }

    @Transactional
    public boolean toggleUserEnableStatus(UUID userId) {
        return userRepository.findById(userId).map(user -> {
            // Save history before changing status
            UserHistory history = userMapper.toHistory(user);
            history.setDisabledRecord(!user.isEnabled()); // log what’s happening
            userHistoryRepository.save(history);

            // Toggle status
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);

            return true;
        }).orElse(false);
    }

    // History methods
    @Transactional
    public boolean markHistoryRecordAsSynced(UUID historyRecordId) {
        int updatedRows = userHistoryRepository.markAsSynced(historyRecordId);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("History record not found: " + historyRecordId);
        }
        return true;
    }
}
