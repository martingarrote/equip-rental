package com.martingarrote.equip_rental.domain.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.domain.rental.RentalEntity;
import com.martingarrote.equip_rental.domain.rental.RentalStatus;
import com.martingarrote.equip_rental.domain.rental.ReturnCondition;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.domain.user.dto.UserResponse;
import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RentalResponse(
        @With UUID id,
        UUID contractId,
        EquipmentResponse equipment,
        UserResponse customer,
        LocalDate startDate,
        LocalDate expectedEndDate,
        LocalDate actualEndDate,
        RentalStatus status,
        BigDecimal totalValue,
        String notes,
        ReturnCondition returnCondition,
        Boolean requiresMaintenance,
        Boolean isOverdue,
        Long daysActive,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static RentalResponse full(RentalEntity entity) {
        Long daysActive = calculateDaysActive(entity);
        Boolean isOverdue = calculateIsOverdue(entity);

        return RentalResponse.builder()
                .id(entity.getId())
                .contractId(entity.getContract().getId())
                .equipment(EquipmentResponse.full(entity.getEquipment()))
                .customer(UserResponse.customer(entity.getCustomer()))
                .startDate(entity.getStartDate())
                .expectedEndDate(entity.getExpectedEndDate())
                .actualEndDate(entity.getActualEndDate())
                .status(entity.getStatus())
                .totalValue(entity.getTotalValue())
                .notes(entity.getNotes())
                .returnCondition(entity.getReturnCondition())
                .requiresMaintenance(entity.getRequiresMaintenance())
                .isOverdue(isOverdue)
                .daysActive(daysActive)
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    public static RentalResponse detailed(RentalEntity entity) {
        Boolean isOverdue = calculateIsOverdue(entity);
        Long daysActive = calculateDaysActive(entity);

        return RentalResponse.builder()
                .id(entity.getId())
                .contractId(entity.getContract().getId())
                .equipment(EquipmentResponse.detailed(entity.getEquipment()))
                .customer(UserResponse.customer(entity.getCustomer()))
                .startDate(entity.getStartDate())
                .expectedEndDate(entity.getExpectedEndDate())
                .actualEndDate(entity.getActualEndDate())
                .status(entity.getStatus())
                .totalValue(entity.getTotalValue())
                .notes(entity.getNotes())
                .returnCondition(entity.getReturnCondition())
                .requiresMaintenance(entity.getRequiresMaintenance())
                .isOverdue(isOverdue)
                .daysActive(daysActive)
                .build();
    }

    public static RentalResponse summary(RentalEntity entity) {
        Boolean isOverdue = calculateIsOverdue(entity);

        return RentalResponse.builder()
                .id(entity.getId())
                .contractId(entity.getContract().getId())
                .equipment(EquipmentResponse.summary(entity.getEquipment()))
                .customer(UserResponse.customer(entity.getCustomer()))
                .startDate(entity.getStartDate())
                .expectedEndDate(entity.getExpectedEndDate())
                .status(entity.getStatus())
                .isOverdue(isOverdue)
                .build();
    }

    private static Boolean calculateIsOverdue(RentalEntity entity) {
        if (entity.getStatus() != RentalStatus.ACTIVE) {
            return false;
        }
        return LocalDate.now().isAfter(entity.getExpectedEndDate());
    }

    private static Long calculateDaysActive(RentalEntity entity) {
        if (entity.getActualEndDate() != null) {
            return ChronoUnit.DAYS.between(entity.getStartDate(), entity.getActualEndDate());
        }
        if (entity.getStatus() == RentalStatus.ACTIVE) {
            return ChronoUnit.DAYS.between(entity.getStartDate(), LocalDate.now());
        }
        return null;
    }}
