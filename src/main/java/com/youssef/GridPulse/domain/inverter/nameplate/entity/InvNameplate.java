package com.youssef.GridPulse.domain.inverter.nameplate.entity;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelEntity;
import com.youssef.GridPulse.domain.inverter.nameplate.enums.DerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * SunSpec Nameplate Model (Model ID 120)
 * Fields use idiomatic camelCase names while column names keep snake_case DB mapping.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class InvNameplate extends SunSpecModelEntity {

    /**
     * SunSpec model identifier (fixed value 1)
     * JSON name: "ID"
     */
    @Builder.Default
    @Column(nullable = false)
    private final int modelId = 120;

    /**
     * Model length
     * JSON name: "L"
     */
    @Builder.Default
    private int modelLength = 0;

    /**
     * Type of DER device (e.g., PV, PV+Storage)
     * JSON name: "DERTyp"
     */
    @Column
    @Enumerated(EnumType.STRING)
    private DerType derType;

    /**
     * Continuous power output capability of the inverter
     * JSON name: "WRtg"
     * Units: W
     */
    @Column
    private Integer wRating;

    /**
     * Scale factor for WRtg
     * JSON name: "WRtg_SF"
     */
    @Column
    private Integer wRatingSf;

    /**
     * Continuous Volt-Ampere capability of the inverter
     * JSON name: "VARtg"
     * Units: VA
     */
    @Column
    private Integer vaRating;

    /**
     * Scale factor for VARtg
     * JSON name: "VARtg_SF"
     */
    @Column
    private Integer vaRatingSf;

    /**
     * Continuous VAR capability in quadrant 1
     * JSON name: "VArRtgQ1"
     * Units: var
     */
    @Column
    private Integer varRatingQ1;

    /**
     * Continuous VAR capability in quadrant 2
     * JSON name: "VArRtgQ2"
     * Units: var
     */
    @Column
    private Integer varRatingQ2;

    /**
     * Continuous VAR capability in quadrant 3
     * JSON name: "VArRtgQ3"
     * Units: var
     */
    @Column
    private Integer varRatingQ3;

    /**
     * Continuous VAR capability in quadrant 4
     * JSON name: "VArRtgQ4"
     * Units: var
     */
    @Column
    private Integer varRatingQ4;

    /**
     * Scale factor for VArRtgQ1–Q4
     * JSON name: "VArRtg_SF"
     */
    @Column
    private Integer varRatingSf;

    /**
     * Maximum RMS AC current capability (sum of all phases)
     * JSON name: "ARtg"
     * Units: A
     */
    @Column
    private Integer acCurrentRating;

    /**
     * Scale factor for ARtg
     * JSON name: "ARtg_SF"
     */
    @Column
    private Integer acCurrentRatingSf;

    /**
     * Minimum power factor in quadrant 1
     * JSON name: "PFRtgQ1"
     * Units: cos()
     */
    @Column
    private Integer pfRatingQ1;

    /**
     * Minimum power factor in quadrant 2
     * JSON name: "PFRtgQ2"
     * Units: cos()
     */
    @Column
    private Integer pfRatingQ2;

    /**
     * Minimum power factor in quadrant 3
     * JSON name: "PFRtgQ3"
     * Units: cos()
     */
    @Column
    private Integer pfRatingQ3;

    /**
     * Minimum power factor in quadrant 4
     * JSON name: "PFRtgQ4"
     * Units: cos()
     */
    @Column
    private Integer pfRatingQ4;

    /**
     * Scale factor for PFRtgQ1–Q4
     * JSON name: "PFRtg_SF"
     */
    @Column
    private Integer pfRatingSf;

    /**
     * Nominal energy rating of storage device
     * JSON name: "WHRtg"
     * Units: Wh
     */
    @Column
    private Integer energyRatingWh;

    /**
     * Scale factor for WHRtg
     * JSON name: "WHRtg_SF"
     */
    @Column
    private Integer energyRatingWhSf;

    /**
     * Usable amp-hour capacity of battery
     * JSON name: "AhrRtg"
     * Units: AH
     */
    @Column
    private Integer ampHourRating;

    /**
     * Scale factor for AhrRtg
     * JSON name: "AhrRtg_SF"
     */
    @Column
    private Integer ampHourRatingSf;

    /**
     * Maximum charge rate into storage device
     * JSON name: "MaxChaRte"
     * Units: W
     */
    @Column
    private Integer maxChargeRate;

    /**
     * Scale factor for MaxChaRte
     * JSON name: "MaxChaRte_SF"
     */
    @Column
    private Integer maxChargeRateSf;

    /**
     * Maximum discharge rate from storage device
     * JSON name: "MaxDisChaRte"
     * Units: W
     */
    @Column
    private Integer maxDischargeRate;

    /**
     * Scale factor for MaxDisChaRte
     * JSON name: "MaxDisChaRte_SF"
     */
    @Column
    private Integer maxDischargeRateSf;

    /**
     * Pad register (reserved)
     * JSON name: "Pad"
     */
    @Column
    private Integer pad;
}