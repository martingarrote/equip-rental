package com.martingarrote.equip_rental.domain.history.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.domain.history.HistoryEntity;
import com.martingarrote.equip_rental.domain.user.dto.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HistoryResponse(
        EquipmentResponse equipment,
        UUID equipmentId,
        String equipmentName,
        String equipmentSerialNumber,
        UserResponse user,
        UUID userId,
        String userName,
        String action,
        LocalDateTime timestamp,
        String description
) {
    public static HistoryResponse full(HistoryEntity entity) {
        return HistoryResponse.builder()
                .equipment(EquipmentResponse.summary(entity.getEquipment()))
                .user(UserResponse.summary(entity.getUser()))
                .action(entity.getAction().getDescription())
                .timestamp(entity.getTimestamp())
                .description(entity.getDescription())
                .build();
    }

    public static HistoryResponse detailed(HistoryEntity entity) {
        return HistoryResponse.builder()
                .equipmentId(entity.getEquipment().getId())
                .equipmentName(entity.getEquipment().getName())
                .equipmentSerialNumber(entity.getEquipment().getSerialNumber())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .action(entity.getAction().getDescription())
                .timestamp(entity.getTimestamp())
                .description(entity.getDescription())
                .build();
    }

    public static HistoryResponse summary(HistoryEntity entity) {
        return HistoryResponse.builder()
                .equipmentId(entity.getEquipment().getId())
                .userId(entity.getUser().getId())
                .action(entity.getAction().getDescription())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
