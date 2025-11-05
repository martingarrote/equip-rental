package com.martingarrote.equip_rental.domain.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.martingarrote.equip_rental.domain.user.Role;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import lombok.Builder;
import lombok.With;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        @With UUID id,
        String name,
        String email,
        Role role,
        String phone,
        String companyName,
        String companyCnpj,
        String department,
        String position,
        Boolean active,
        String token
) {
    public static UserResponse detailed(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .companyName(user.getCompanyName())
                .companyCnpj(user.getCompanyCnpj())
                .department(user.getDepartment())
                .position(user.getPosition())
                .active(user.isActive())
                .build();
    }
    
    public static UserResponse summary(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }

    public static UserResponse token(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
