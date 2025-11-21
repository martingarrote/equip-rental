package com.martingarrote.equip_rental.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "{user.name.notBlank}")
        @Size(min = 2, max = 100, message = "{user.name.size}")
        String name,

        @NotBlank(message = "{user.email.notBlank}")
        @Email(message = "{user.email.valid}")
        String email,

        @NotBlank(message = "{user.password.notBlank}")
        String password,

        @NotBlank(message = "{user.phone.notBlank}")
        @Size(min = 10, max = 20, message = "{user.phone.size}")
        @Pattern(regexp = "^[0-9+\\(\\)\\s-]*$", message = "{user.phone.invalid}")
        String phone,

        @Size(max = 100, message = "{user.companyName.size}")
        String companyName,

        @Size(max = 18, message = "{user.companyCnpj.size}")
        @Pattern(regexp = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})?$", message = "{user.companyCnpj.invalid}")
        String companyCnpj,

        @Size(max = 50, message = "{user.department.size}")
        String department,

        @Size(max = 50, message = "{user.position.size}")
        String position
) {
}
