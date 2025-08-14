package com.youssef.GridPulse.service;

import com.youssef.GridPulse.domain.dto.InverterInput;
import com.youssef.GridPulse.domain.entity.Inverter;
import com.youssef.GridPulse.domain.entity.InverterHistory;
import com.youssef.GridPulse.domain.mapper.InverterMapper;
import com.youssef.GridPulse.repository.InverterHistoryRepository;
import com.youssef.GridPulse.repository.InverterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InverterService {

    private final InverterRepository repository;
    private final InverterHistoryRepository historyRepository;
    private final InverterMapper inverterMapper;

    @Transactional
    public Inverter createInverter(InverterInput input) {
        Inverter inverter = inverterMapper.toEntity(input);
        Inverter savedInverter = repository.save(inverter);

        // Save audit history
        InverterHistory history = inverterMapper.toHistory(savedInverter);
        history.setOriginalId(savedInverter.getId());
        history.setCreatedRecord(true);
        historyRepository.save(history);

        return savedInverter;
    }

    public List<Inverter> getAllInverters() {
        return repository.findAll();
    }

    public Inverter getInverterById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inverter not found"));
    }

    @Transactional
    public Inverter updateInverter(UUID id, InverterInput input) {
        Inverter inverter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inverter not found"));

        // Keep a history snapshot before update
        InverterHistory historyBefore = inverterMapper.toHistory(inverter);
        historyBefore.setOriginalId(inverter.getId());
        historyBefore.setUpdatedRecord(true);
        historyRepository.save(historyBefore);

        // Update fields
        inverter.setName(input.name());
        inverter.setModel(input.model());
        inverter.setVersion(input.version());
        inverter.setManufacturer(input.manufacturer());

        return repository.save(inverter);
    }

    @Transactional
    public boolean deleteInverter(UUID id) {
        Inverter inverter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inverter not found"));

        // Save history before deletion
        InverterHistory historyBeforeDelete = inverterMapper.toHistory(inverter);
        historyBeforeDelete.setOriginalId(inverter.getId());
        historyBeforeDelete.setDeletedRecord(true);
        historyRepository.save(historyBeforeDelete);

        repository.delete(inverter);
        return true;
    }

    // History methods

    public List<InverterHistory> getAllInverterHistory() {
        return historyRepository.findAll();
    }

    public List<InverterHistory> getInverterHistory(UUID id) {
        return historyRepository.findByOriginalId(id);
    }

    public InverterHistory getInverterHistoryById(UUID historyId) {
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("Inverter history not found"));
    }

    @Transactional
    public boolean markHistoryRecordAsSynced(UUID historyRecordId) {
        int updatedRows = historyRepository.markAsSynced(historyRecordId);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("History record not found: " + historyRecordId);
        }
        return true;
    }

}
