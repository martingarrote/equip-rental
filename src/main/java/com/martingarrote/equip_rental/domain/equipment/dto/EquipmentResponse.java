package com.martingarrote.equip_rental.domain.equipment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EquipmentResponse(
        @With UUID id,
        String name,
        String type,
        String serialNumber,
        String status,
        LocalDate acquisitionDate,
        BigDecimal acquisitionValue,
        BigDecimal dailyRentalCost,
        LocalDate nextPreventiveMaintenance,
        Integer maintenancePeriodDays,
        String notes,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static EquipmentResponse full(EquipmentEntity equipment) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .type(equipment.getType().getDescription())
                .serialNumber(equipment.getSerialNumber())
                .status(equipment.getStatus().getDescription())
                .acquisitionDate(equipment.getAcquisitionDate())
                .acquisitionValue(equipment.getAcquisitionValue())
                .dailyRentalCost(equipment.getDailyRentalCost())
                .nextPreventiveMaintenance(equipment.getNextPreventiveMaintenance())
                .maintenancePeriodDays(equipment.getMaintenancePeriodDays())
                .notes(equipment.getNotes())
                .createdAt(equipment.getCreatedAt())
                .createdBy(equipment.getCreatedBy())
                .updatedAt(equipment.getUpdatedAt())
                .updatedBy(equipment.getUpdatedBy())
                .build();
    }

    public static EquipmentResponse detailed(EquipmentEntity equipment) {
        return EquipmentResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .type(equipment.getType().getDescription())
                .serialNumber(equipment.getSerialNumber())
                .status(equipment.getStatus().getDescription())
                .dailyRentalCost(equipment.getDailyRentalCost())
                .notes(equipment.getNotes())
                .build();
    }

    public static EquipmentResponse summary(EquipmentEntity equipment) {
        return EquipmentResponse.builder()
                .name(equipment.getName())
                .type(equipment.getType().getDescription())
                .serialNumber(equipment.getSerialNumber())
                .status(equipment.getStatus().getDescription())
                .build();
    }
}
