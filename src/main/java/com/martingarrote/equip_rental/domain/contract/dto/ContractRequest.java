package com.martingarrote.equip_rental.domain.contract.dto;

import com.martingarrote.equip_rental.domain.contract.ContractStatus;
import com.martingarrote.equip_rental.domain.rental.dto.RentalItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ContractRequest(

        @NotEmpty(message = "{contract.request.rentalItems.notEmpty}")
        List<@Valid @NotNull RentalItemRequest> rentalItems,

        UUID customerId,

        @NotNull(message = "{contract.startDate.notNull}")
        LocalDate startDate,

        @NotNull(message = "{contract.endDate.notNull}")
        LocalDate endDate,

        @Size(max = 500, message = "{contract.notes.size}")
        String notes

) {
}
