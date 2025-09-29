package com.youssef.GridPulse.domain.base;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseService<E extends BaseEntity, H extends BaseHistoryEntity, ID extends UUID, INPUT> {

    protected final JpaRepository<E, ID> repository;
    protected final BaseHistoryRepository<H, ID> historyRepository;
    protected final BaseMapper<E, H, INPUT> mapper;

    @Transactional
    public E create(INPUT input) {
        try{
        E entity = mapper.toEntity(input);
        entity.setId(null);
        E saved = repository.saveAndFlush(entity);

        H history = mapper.toHistory(saved);
        setCreated(history, getId(saved));
        historyRepository.saveAndFlush(history);

        return saved;
    }catch (ObjectOptimisticLockingFailureException e){
            throw new RuntimeException("Conflict occurred while creating entity. Please try again.", e);
        }
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public E getEntityById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Transactional
    public E update(ID id, INPUT input) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        H historyBefore = mapper.toHistory(entity);
        setUpdated(historyBefore, id);
        historyRepository.save(historyBefore);

        mapper.updateEntity(input, entity);
        return repository.save(entity);
    }

    @Transactional
    public boolean delete(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        H history = mapper.toHistory(entity);
        setDeleted(history, id);
        historyRepository.save(history);

        repository.delete(entity);
        return true;
    }

    // History accessors

    /**
     * Finds all history records of an entity.
     * @return all history records of the entity
     */
    public List<H> findAllHistory() {

        return historyRepository.findAll();
    }

    /**
     * Finds all history records for a given original entity ID.
     * @param id the ID of the original entity record
     * @return history of a given entity record
     */
    public List<H> findHistoryByOriginalId(ID id) {
        return historyRepository.findByOriginalId(id);
    }

    /**
     * Finds a specific history record by its ID.
     * @param historyId the ID of the history record to find
     * @return the history record if found
     */
    public H findHistoryById(ID historyId) {
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new EntityNotFoundException("History not found"));
    }

    /**
     * Marks a history record as synced.
     * @param historyRecordId the ID of the history record to mark as synced
     * @return true if the record was successfully marked as synced
     */
    @Transactional
    public boolean markHistoryRecordAsSynced(ID historyRecordId) {
        int updatedRows = historyRepository.markAsSynced(historyRecordId);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("History record not found: " + historyRecordId);
        }
        return true;
    }


    // --- Hooks/Helpers for history flags ---
    protected abstract ID getId(E entity);

    protected void setCreated(H history, ID id) {
        history.setId(null);
        history.setOriginalId(id);
        history.setCreatedRecord(true);
    }
    protected void setUpdated(H history, ID id) {
        history.setId(null);
        history.setOriginalId(id);
        history.setUpdatedRecord(true);
    }
    protected void setDeleted(H history, ID id) {
        history.setId(null);
        history.setOriginalId(id);
        history.setDeletedRecord(true);
    }
}
