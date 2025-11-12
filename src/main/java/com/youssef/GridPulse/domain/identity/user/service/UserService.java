package com.youssef.GridPulse.domain.identity.user.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.dto.UpdateUserInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.mapper.UserMapper;
import com.youssef.GridPulse.domain.identity.user.repository.UserHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService<User, UserHistory, UUID, RegisterInput> {

    private final UserMapper mapper2;

    public UserService(UserRepository repository, UserHistoryRepository historyRepository, UserMapper mapper, UserMapper mapper2) {
        super(repository, historyRepository, mapper);

        this.mapper2 = mapper2;
    }

    public List<UserHistory> getUsersActivityHistory() {
        return super.findAllHistory();
    }

    @Transactional
    public User updateUser(UUID id, UpdateUserInput input) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserHistory history = mapper.toHistory(user);
        setUpdated(history, id);
        historyRepository.save(history);

        mapper2.updateEntity(input, user); // uses MapStruct partial update
        return repository.save(user);
    }


    @Transactional
    public boolean toggleUserEnableStatus(UUID userId) {
        return repository.findById(userId).map(user -> {
            // Save history BEFORE changing status
            UserHistory history = mapper.toHistory(user);

            history.setEnabled(user.isEnabled()); // Record the state before toggle

            historyRepository.save(history);

            // Toggle the actual user status
            user.setEnabled(!user.isEnabled());
            repository.save(user);

            return true;
        }).orElse(false);
    }
}
