package com.martingarrote.equip_rental.domain.rental.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RentalItemRequest(

        @NotNull(message = "{rental.equipment.notNull}")
        UUID equipmentId,

        @Size(max = 500, message = "{rental.notes.size}")
        String notes

) {
}
