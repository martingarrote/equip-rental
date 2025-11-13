package com.martingarrote.equip_rental.domain.equipment.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.equipment.EquipmentStatus;
import com.martingarrote.equip_rental.domain.equipment.EquipmentType;
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
        EquipmentType type,
        String serialNumber,
        EquipmentStatus status,
        LocalDate acquisitionDate,
        BigDecimal acquisitionValue,
        LocalDate nextPreventiveMaintenance,
        Integer maintenancePeriodDays,
        String notes,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static EquipmentResponse detailed(EquipmentEntity equipment) {
        return EquipmentResponse.builder()
                .name(equipment.getName())
                .type(equipment.getType())
                .serialNumber(equipment.getSerialNumber())
                .status(equipment.getStatus())
                .acquisitionDate(equipment.getAcquisitionDate())
                .acquisitionValue(equipment.getAcquisitionValue())
                .nextPreventiveMaintenance(equipment.getNextPreventiveMaintenance())
                .maintenancePeriodDays(equipment.getMaintenancePeriodDays())
                .notes(equipment.getNotes())
                .createdAt(equipment.getCreatedAt())
                .createdBy(equipment.getCreatedBy())
                .updatedAt(equipment.getUpdatedAt())
                .updatedBy(equipment.getUpdatedBy())
                .build();
    }
    
    public static EquipmentResponse summary(EquipmentEntity equipment) {
        return EquipmentResponse.builder()
                .name(equipment.getName())
                .type(equipment.getType())
                .serialNumber(equipment.getSerialNumber())
                .status(equipment.getStatus())
                .build();
    }
}
