package com.youssef.GridPulse.domain.security.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.domain.security.dto.ImportSecurityKeyInput;
import com.youssef.GridPulse.domain.security.dto.SecurityKeyInput;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.entity.SecurityKeyHistory;
import com.youssef.GridPulse.domain.security.enums.KeyStatus;
import com.youssef.GridPulse.domain.security.mapper.SecurityKeyMapper;
import com.youssef.GridPulse.domain.security.repository.SecurityKeyHistoryRepository;
import com.youssef.GridPulse.domain.security.repository.SecurityKeyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SecurityKeyService extends BaseService<SecurityKey, SecurityKeyHistory, UUID, SecurityKeyInput> {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final EncryptionService encryptionService;

    public SecurityKeyService(SecurityKeyRepository repository, SecurityKeyHistoryRepository historyRepository, SecurityKeyMapper mapper,

                              UserRepository userRepository,
                              DeviceRepository deviceRepository,

                              EncryptionService encryptionService
    ) {
        super(repository, historyRepository, mapper);
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.encryptionService = encryptionService;
    }


    @Override
    @Transactional
    protected void beforeSave(SecurityKey entity, SecurityKeyInput input) {
        var owner = userRepository.findById(input.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        var device =  deviceRepository.findById(input.deviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        entity.setOwner(owner);
        entity.setDevice(device);

        // Mock private key for demo purposes
        // Simulate private key encryption for demo
        entity.setPrivateKey(encryptionService.encrypt("MOCK_PRIVATE_KEY_FOR_" + input.name()));

    }

    @Override
    @Transactional
    protected void setRelationsInHistory(SecurityKeyHistory history, SecurityKey entity) {
        history.setOwnerId(entity.getOwner().getId());
        history.setDeviceId(entity.getDevice().getId());

    }

    @Transactional
    public SecurityKey importKey(ImportSecurityKeyInput input) {

        var owner = userRepository.findById(input.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        var device =  deviceRepository.findById(input.deviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        SecurityKey entity = new SecurityKey();

        entity.setName(input.name());
        entity.setDevice(device);
        entity.setOwner(owner);
        entity.setSecurityType(input.securityType());
        entity.setSerialNumber(input.serialNumber());
        entity.setPublicKey(input.publicKey());
        entity.setKeySource(input.keySource());
        entity.setStatus(KeyStatus.ACTIVE);
        entity.setRevokedTimestamp(input.revokedTimestamp());

        // 🔐 Encrypt the private key before saving
        String encrypted = encryptionService.encrypt(input.privateKey());
        entity.setPrivateKey(encrypted);

        SecurityKey saved = repository.saveAndFlush(entity);

        SecurityKeyHistory history = mapper.toHistory(saved);
        setCreated(history, saved.getId());
        historyRepository.saveAndFlush(history);

        return saved;
    }

}
