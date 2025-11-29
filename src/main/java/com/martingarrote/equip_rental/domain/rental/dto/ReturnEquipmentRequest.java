package com.martingarrote.equip_rental.domain.rental.dto;

import com.martingarrote.equip_rental.domain.rental.ReturnCondition;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReturnEquipmentRequest(

        @NotNull(message = "{rental.returnEquipmentRequest.returnCondition.notNull}")
        ReturnCondition returnCondition,

        @NotNull(message = "{rental.returnEquipmentRequest.requiresMaintenance.notNull}")
        Boolean requiresMaintenance,

        @Size(max = 500, message = "{rental.returnEquipmentRequest.notes.size}")
        String notes
) {
}
