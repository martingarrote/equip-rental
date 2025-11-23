package com.martingarrote.equip_rental.domain.contract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.contract.ContractEntity;
import com.martingarrote.equip_rental.domain.contract.ContractStatus;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractResponse(
        @With UUID id,
        UUID customerId,
        String customerName,
        LocalDate startDate,
        LocalDate endDate,
        ContractStatus status,
        BigDecimal totalValue,
        String notes,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static ContractResponse detailed(ContractEntity entity) {
        return ContractResponse.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer().getId())
                .customerName(entity.getCustomer().getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .totalValue(entity.getTotalValue())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public static ContractResponse summary(ContractEntity entity) {
        return ContractResponse.builder()
                .id(entity.getId())
                .customerName(entity.getCustomer().getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .totalValue(entity.getTotalValue())
                .build();
    }
}
