package com.martingarrote.equip_rental.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeResponse(
        @With UUID id,
        String name,
        String email,
        String phone,
        String department,
        String position,
        Boolean active,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static EmployeeResponse detailed(UserEntity user) {
        return EmployeeResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .position(user.getPosition())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }

    public static EmployeeResponse summary(UserEntity user) {
        return EmployeeResponse.builder()
                .name(user.getName())
                .position(user.getPosition())
                .build();
    }
}
