package com.martingarrote.equip_rental.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerResponse(
        @With UUID id,
        String name,
        String email,
        String phone,
        String companyName,
        String companyCnpj,
        Boolean active,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {
    public static CustomerResponse detailed(UserEntity user) {
        return CustomerResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .companyName(user.getCompanyName())
                .companyCnpj(user.getCompanyCnpj())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }

    public static CustomerResponse summary(UserEntity user) {
        return CustomerResponse.builder()
                .name(user.getName())
                .companyName(user.getCompanyName())
                .build();
    }
}
