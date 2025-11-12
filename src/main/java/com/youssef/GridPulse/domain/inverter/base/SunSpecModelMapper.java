package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseMapper;
import org.mapstruct.*;

/**
 * Generic mapper interface for SunSpec-based model entities.
 * <p>
 * Extends {@link BaseMapper} to provide standardized mapping logic for:
 * <ul>
 *   <li>Mapping input DTOs to SunSpec model entities</li>
 *   <li>Generating auditable history records from entities</li>
 *   <li>Partial updates with null-safe property mapping</li>
 * </ul>
 *
 * <p>This interface introduces SunSpec-specific mapping conventions:
 * <ul>
 *   <li>{@code modelId} and {@code modelLength} are core SunSpec fields and are handled at the entity level.
 *       {@code modelId} is treated as a constant per model type and is set explicitly in each concrete mapper implementation.</li>
 *   <li>{@code inverterId} is extracted from the parent {@code Inverter} relationship and mapped into history records for traceability.</li>
 * </ul>
 *
 * @param <S>  The SunSpec model entity type (e.g., {@code InvCommonEntity})
 * @param <SH> The corresponding history entity type (e.g., {@code InvCommonHistory})
 * @param <IN> The input DTO type (e.g., {@code InvCommonInput})
 */
public interface SunSpecModelMapper<S extends SunSpecModelEntity, SH extends SunSpecModelEntityHistory, IN extends SunSpecModelInput>
        extends BaseMapper<S, SH, IN> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modelId", ignore = true) // Ignored because it is a constant for each model. It will be set in each override of InvXMapper.toEntity
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "source", constant = "APP")
    S toEntity(IN input);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originalId", source = "id")
    @Mapping(target = "inverterId", source = "inverter.id")
    @Mapping(target = "modelId", source = "modelId")
    @Mapping(target = "modelLength", source = "modelLength")
    @Mapping(target = "createdRecord", constant = "false")
    @Mapping(target = "updatedRecord", constant = "false")
    @Mapping(target = "deletedRecord", constant = "false")
    @Mapping(target = "synced", constant = "false")
    SH toHistory(S entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(IN input, @MappingTarget S entity);
}
