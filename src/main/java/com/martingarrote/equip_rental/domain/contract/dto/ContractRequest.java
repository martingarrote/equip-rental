package com.martingarrote.equip_rental.domain.contract.dto;

import com.martingarrote.equip_rental.domain.contract.ContractStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record ContractRequest(
        @NotNull(message = "{contract.customer.notNull}", groups = EmployeeCreation.class)
        UUID customerId,

        @NotNull(message = "{contract.status.notNull}", groups = EmployeeCreation.class)

        ContractStatus status,

        @NotNull(message = "{contract.startDate.notNull}")
        LocalDate startDate,

        @NotNull(message = "{contract.endDate.notNull}")
        LocalDate endDate,

        @Size(max = 500, message = "{contract.notes.size}")
        String notes

) {

    public interface EmployeeCreation {
    }
}
