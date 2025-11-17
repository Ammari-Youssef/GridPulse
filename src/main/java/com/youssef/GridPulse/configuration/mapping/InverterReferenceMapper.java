package com.youssef.GridPulse.configuration.mapping;

import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import org.mapstruct.Mapper;

import java.util.UUID;

/**
 * Utility Mapper for converting entity references from UUIDs to entity instances.
 * This is useful for mapping foreign key references in DTOs to their corresponding entities.
 * Mainly, mapping an Inverter ID (UUID) to its corresponded Inverter entity.
 */
@Mapper(componentModel = "spring")
public interface InverterReferenceMapper {
    default Inverter map(UUID id) {
        if (id == null) return null;
        Inverter inverter = new Inverter();
        inverter.setId(id);
        return inverter;
    }

}

