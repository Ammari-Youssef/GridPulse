package com.youssef.GridPulse.common.base;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseResolver<E extends BaseEntity, H extends BaseHistoryEntity, ID extends UUID, INPUT> {

    protected final BaseService<E, H, ID, INPUT> service;

    // Common CRUD operations
    public List<E> getAll() {
        return service.getAll();
    }

    public E getById(ID id) {
        return service.getEntityById(id);
    }

    public E create(INPUT input) {
        return service.create(input);
    }

    public E update(ID id, INPUT input) {
        return service.update(id, input);
    }

    public boolean delete(ID id) {
        return service.delete(id);
    }

    // Common history operations
    public List<H> getHistory(ID originalId) {
        return service.findHistoryByOriginalId(originalId);
    }

    public List<H> getAllHistory() {
        return service.findAllHistory();
    }

    public H getHistoryById(ID historyId) {
        return service.findHistoryById(historyId);
    }

    public Boolean markHistorySynced(ID id) {
        return service.markHistoryRecordAsSynced(id);
    }
}
