package com.martingarrote.equip_rental.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import lombok.Builder;
import lombok.With;

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
        Boolean active
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
                .build();
    }

    public static CustomerResponse summary(UserEntity user) {
        return CustomerResponse.builder()
                .name(user.getName())
                .companyName(user.getCompanyName())
                .build();
    }
}
