package com.martingarrote.equip_rental.domain.contract.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContractActionRequest(

        @NotBlank(message = "{contract.actionRequest.reason.notBlank}")
        @Size(max = 500, message = "{contract.actionRequest.reason.size}")
        String reason

) {
}
