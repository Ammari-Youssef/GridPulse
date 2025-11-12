package com.youssef.GridPulse.domain.inverter.settings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelInput;
import com.youssef.GridPulse.domain.inverter.settings.enums.*;

import java.util.UUID;

/**
 * JSON mapping preserves original SunSpec point names (e.g. "ID", "L", "Mn").
 * Side Note: @JsonProperty annotations are included for future REST API compatibility.
 * These annotations do not affect GraphQL behavior, as field mapping is defined by the GraphQL schema.
 */
public record InvSettingsInput(

        @JsonProperty("ID")
        @NotNull(message = "Inverter ID is required.")
        UUID inverterId,

        @JsonProperty("L")
        @NotNull(message = "Model length is required.")
        Integer modelLength,

        @JsonProperty("WMax")
        Integer wMax,

        @JsonProperty("VRef")
        Integer vRef,

        @JsonProperty("VRefOfs")
        Integer vRefOfs,

        @JsonProperty("VMax")
        Integer vMax,

        @JsonProperty("VMin")
        Integer vMin,

        @JsonProperty("VAMax")
        Integer vaMax,

        @JsonProperty("VArMaxQ1")
        Integer varMaxQ1,

        @JsonProperty("VArMaxQ2")
        Integer varMaxQ2,

        @JsonProperty("VArMaxQ3")
        Integer varMaxQ3,

        @JsonProperty("VArMaxQ4")
        Integer varMaxQ4,

        @JsonProperty("WGra")
        Integer wGra,

        @JsonProperty("PFMinQ1")
        Integer pfMinQ1,

        @JsonProperty("PFMinQ2")
        Integer pfMinQ2,

        @JsonProperty("PFMinQ3")
        Integer pfMinQ3,

        @JsonProperty("PFMinQ4")
        Integer pfMinQ4,

        @JsonProperty("VArAct")
        VarAction varAct,

        @JsonProperty("ClcTotVA")
        ClcTotVaMethod clcTotVa,

        @JsonProperty("MaxRmpRte")
        Integer maxRmpRte,

        @JsonProperty("ECPNomHz")
        Integer ecpNomHz,

        @JsonProperty("ConnPh")
        ConnPhase connPh
) implements SunSpecModelInput {

    @Override
    public UUID getInverterId() {
        return inverterId;
    }
}