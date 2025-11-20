package com.martingarrote.equip_rental.domain.equipment.dto;

import com.martingarrote.equip_rental.domain.equipment.EquipmentStatus;
import com.martingarrote.equip_rental.domain.equipment.EquipmentType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EquipmentRequest(

        @NotBlank(message = "{equipment.name.notBlank}")
        @Size(min = 2, max = 100, message = "{equipment.name.size}")
        String name,

        @NotNull(message = "{equipment.type.notNull}")
        EquipmentType type,

        @NotBlank(message = "{equipment.serialNumber.notBlank}")
        @Size(min = 3, max = 50, message = "{equipment.serialNumber.size}")
        String serialNumber,

        @NotNull(message = "{equipment.status.notNull}")
        EquipmentStatus status,

        @NotNull(message = "{equipment.acquisitionDate.notNull}")
        @PastOrPresent(message = "{equipment.acquisitionDate.pastOrPresent}")
        LocalDate acquisitionDate,

        @NotNull(message = "{equipment.acquisitionValue.notNull}")
        @DecimalMin(value = "0.0", inclusive = false, message = "{equipment.acquisitionValue.positive}")
        @Digits(integer = 10, fraction = 2, message = "{equipment.acquisitionValue.format}")
        BigDecimal acquisitionValue,

        @NotNull(message = "{equipment.dailyRentalCost.notNull}")
        @DecimalMin(value = "0.0", inclusive = false, message = "{equipment.dailyRentalCost.positive}")
        @Digits(integer = 10, fraction = 2, message = "{equipment.dailyRentalCost.format}")
        BigDecimal dailyRentalCost,

        @NotNull(message = "{equipment.nextPreventiveMaintenance.notNull}")
        @FutureOrPresent(message = "{equipment.nextPreventiveMaintenance.futureOrPresent}")
        LocalDate nextPreventiveMaintenance,

        @NotNull(message = "{equipment.maintenancePeriodDays.notNull}")
        @Min(value = 1, message = "{equipment.maintenancePeriodDays.min}")
        @Max(value = 1095, message = "{equipment.maintenancePeriodDays.max}")
        Integer maintenancePeriodDays,

        @Size(max = 500, message = "{equipment.notes.size}")
        String notes
) {

    public EquipmentRequest {
        if (status == null) {
            status = EquipmentStatus.AVAILABLE;
        }
    }
}
