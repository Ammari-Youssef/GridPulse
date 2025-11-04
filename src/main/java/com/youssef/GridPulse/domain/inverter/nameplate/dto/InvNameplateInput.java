package com.youssef.GridPulse.domain.inverter.nameplate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelInput;
import jakarta.validation.constraints.*;

import java.util.UUID;

/**
 * JSON mapping preserves original SunSpec point names (e.g. "ID", "L", "Mn").
 * Side Note: @JsonProperty annotations are included for future REST API compatibility.
 * These annotations do not affect GraphQL behavior, as field mapping is defined by the GraphQL schema.
 */
public record InvNameplateInput(

        @JsonProperty("ID")
        @NotNull(message = "Inverter ID is required.")
        UUID inverterId,

        @JsonProperty("L")
        @NotNull(message = "Model length is required.")
        Integer modelLength,

        @JsonProperty("DERTyp")
        Integer derType,

        @JsonProperty("WRtg")
        Integer wRating,

        @JsonProperty("WRtg_SF")
        Integer wRatingSf,

        @JsonProperty("VARtg")
        Integer vaRating,

        @JsonProperty("VARtg_SF")
        Integer vaRatingSf,

        @JsonProperty("VArRtgQ1")
        Integer varRatingQ1,

        @JsonProperty("VArRtgQ2")
        Integer varRatingQ2,

        @JsonProperty("VArRtgQ3")
        Integer varRatingQ3,

        @JsonProperty("VArRtgQ4")
        Integer varRatingQ4,

        @JsonProperty("VArRtg_SF")
        Integer varRatingSf,

        @JsonProperty("ARtg")
        Integer acCurrentRating,

        @JsonProperty("ARtg_SF")
        Integer acCurrentRatingSf,

        @JsonProperty("PFRtgQ1")
        Integer pfRatingQ1,

        @JsonProperty("PFRtgQ2")
        Integer pfRatingQ2,

        @JsonProperty("PFRtgQ3")
        Integer pfRatingQ3,

        @JsonProperty("PFRtgQ4")
        Integer pfRatingQ4,

        @JsonProperty("PFRtg_SF")
        Integer pfRatingSf,

        @JsonProperty("WHRtg")
        Integer energyRatingWh,

        @JsonProperty("WHRtg_SF")
        Integer energyRatingWhSf,

        @JsonProperty("AhrRtg")
        Integer ampHourRating,

        @JsonProperty("AhrRtg_SF")
        Integer ampHourRatingSf,

        @JsonProperty("MaxChaRte")
        Integer maxChargeRate,

        @JsonProperty("MaxChaRte_SF")
        Integer maxChargeRateSf,

        @JsonProperty("MaxDisChaRte")
        Integer maxDischargeRate,

        @JsonProperty("MaxDisChaRte_SF")
        Integer maxDischargeRateSf,

        @JsonProperty("Pad")
        Integer pad

) implements SunSpecModelInput {

        @Override
        public UUID getInverterId() {
                return inverterId;
        }
}