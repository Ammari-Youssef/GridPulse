package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.List;

/**
 * Generic service layer for SunSpec model entities and their history records.
 * <p>
 * This service inherits core CRUD and audit operations from {@link BaseService},
 * including entity creation, update, deletion, and history tracking.
 * It is designed to support any SunSpec-compliant model by leveraging generics.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * <ul>
 *   <li>Resolve and inject {@link Inverter} relationships before saving</li>
 *   <li>Map entity relations into history records</li>
 *   <li>Provide lookup methods by inverter ID for both entities and history</li>
 * </ul>
 * </p>
 *
 * @param <S>     SunSpec model entity type
 * @param <SH>    SunSpec model history entity type
 * @param <ID>    Identifier type (UUID)
 * @param <INPUT> Input DTO type implementing {@link SunSpecModelInput}
 */
public class SunSpecModelService<S extends SunSpecModelEntity, SH extends SunSpecModelEntityHistory, ID extends UUID, INPUT extends SunSpecModelInput>
        extends BaseService<S, SH, ID, INPUT> {

    // Repository for SunSpec model entities; stored separately for direct access to custom queries like findByInverter_Id
    private final SunSpecModelRepository<S, ID> repository;

    // Repository for SunSpec model history entities; used for queries like findByInverterId
    private final SunSpecModelHistoryRepository<SH, ID> historyRepository;

    // Repository for resolving and injecting Inverter relationships into SunSpec entities and their history records
    private final InverterRepository inverterRepository;

    public SunSpecModelService(
            SunSpecModelRepository<S, ID> repository,
            SunSpecModelHistoryRepository<SH, ID> historyRepository,
            SunSpecModelMapper<S, SH, INPUT> mapper,

            InverterRepository inverterRepository) {
        super(repository, historyRepository, mapper);

        this.repository = repository; // Stored for direct access to entity-level queries
        this.historyRepository = historyRepository; // Stored for history-specific queries
        this.inverterRepository = inverterRepository; // Used to resolve Inverter references

    }

    /**
     * Retrieves all SunSpec model entities associated with the given inverter ID.
     *
     * @param inverterId UUID of the inverter
     * @return List of matching SunSpec model entities
     */
    protected List<S> findByInverterId(ID inverterId) {
        return repository.findByInverter_Id(inverterId);
    }

    /**
     * Retrieves all history records for SunSpec model entities associated with the given inverter ID.
     *
     * @param inverterId UUID of the inverter
     * @return List of matching SunSpec model history records
     */
    public List<SH> findHistoryByInverterId(ID inverterId) {
        return historyRepository.findByInverterId(inverterId);
    }


    @Override
    protected void beforeSave(S entity, INPUT input) {
        super.beforeSave(entity, input);
        entity.setInverter(resolveInverter(input.getInverterId()));
    }

    @Override
    protected void setRelationsInHistory(SH history, S entity) {
        super.setRelationsInHistory(history, entity);
        history.setInverterId(entity.getInverter().getId());
    }

    private Inverter resolveInverter(UUID inverterId) {
        return inverterRepository.findById(inverterId)
                .orElseThrow(() -> new EntityNotFoundException("Inverter not found"));
    }

    // Pagination methods
    public PageResponse<S> findByInverterIdOffsetBased(UUID inverterId, PageRequestInput pageRequest) {
        Pageable pageable = BaseService.setPageRequestFields(pageRequest);
        Page<S> result = repository.findByInverter_Id(inverterId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    public PageResponse<SH> findHistoryByInverterIdOffsetBased(UUID inverterId, PageRequestInput pageRequest) {
        Pageable pageable = BaseService.setPageRequestFields(pageRequest);
        Page<SH> result = historyRepository.findByInverterId(inverterId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }
}
