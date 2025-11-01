package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseResolver;

import java.util.List;
import java.util.UUID;

/**
 * Abstract GraphQL resolver for SunSpec model entities and their history records.
 * <p>
 * This resolver inherits core query and mutation operations from {@link BaseResolver},
 * including CRUD functionality and audit support for SunSpec-compliant models.
 * It is designed to be extended by concrete resolvers such as {@code InvCommonResolver}.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * <ul>
 *   <li>Expose GraphQL queries for retrieving SunSpec entities by inverter ID</li>
 *   <li>Expose GraphQL queries for retrieving history records by inverter ID</li>
 *   <li>Delegate business logic to {@link SunSpecModelService}</li>
 * </ul>
 * </p>
 *
 * @param <S>  SunSpec model entity type
 * @param <SH> SunSpec model history entity type
 * @param <ID> Identifier type (UUID)
 * @param <IN> Input DTO type implementing {@link SunSpecModelInput}
 */
public abstract class SunSpecModelResolver<S extends SunSpecModelEntity, SH extends SunSpecModelEntityHistory, ID extends UUID, IN extends SunSpecModelInput>
        extends BaseResolver<S, SH, ID, IN> {

    protected final SunSpecModelService<S, SH, ID, IN> service;

    public SunSpecModelResolver(SunSpecModelService<S, SH, ID, IN> service) {
        super(service);
        this.service = service;
    }

    /**
     * Retrieves all SunSpec model entities associated with the given inverter ID.
     * Intended for GraphQL query resolution.
     *
     * @param inverterId UUID of the inverter
     * @return List of matching SunSpec model entities
     */
    public List<S> getAllByInverterId(ID inverterId) {
        return service.findByInverterId(inverterId);
    }

    /**
     * Retrieves all history records for SunSpec model entities associated with the given inverter ID.
     * Intended for GraphQL query resolution.
     *
     * @param inverterId UUID of the inverter
     * @return List of matching SunSpec model history records
     */
    public List<SH>
    getAllHistoryByInverterId(ID inverterId) {
        return service.findHistoryByInverterId(inverterId);
    }

}
