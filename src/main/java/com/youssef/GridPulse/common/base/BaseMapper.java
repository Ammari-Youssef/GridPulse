package com.youssef.GridPulse.common.base;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface BaseMapper<E extends BaseEntity , H extends BaseHistoryEntity, INPUT> {

    /**
     * Convert entity to history record.
     * @param input DTO / Record
     * @return Entity instance
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP") // default value can be either 'app' or 'sync'
    E toEntity(INPUT input);

    /**
     * Convert history to entity record.
     * @param entity Entity instance
     * @return EntityHistory instance
     */
    @Mapping(target = "id", ignore = true) // history has its own ID
    @Mapping(target = "originalId", source = "id") // link to original inverter
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    H toHistory(E entity);

    // Optional hook if you want to update fields instead of overwriting
    void updateEntity(INPUT input, @MappingTarget E entity);

}
