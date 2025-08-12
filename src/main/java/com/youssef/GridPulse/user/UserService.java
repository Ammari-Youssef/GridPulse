package com.youssef.GridPulse.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public boolean markHistoryRecordAsSynced(UUID historyRecordId) {
        int updatedRows = userHistoryRepository.markAsSynced(historyRecordId);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("History record not found: " + historyRecordId);
        }
        return true;
    }
}
