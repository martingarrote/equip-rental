package com.martingarrote.equip_rental.domain.rental.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record RentalItemRequest(
        @NotNull(message = "{rental.equipment.notNull}")
        UUID equipmentId,

        @NotNull(message = "{rental.startDate.notNull}")
        LocalDate startDate,

        @NotNull(message = "{rental.expectedEndDate.notNull}")
        LocalDate expectedEndDate,

        @Size(max = 500, message = "{rental.notes.size}")
        String notes
) {
}
