package com.martingarrote.equip_rental.domain.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "{user.email.notBlank}")
        @Email(message = "{user.email.valid}")
        String email,
        
        @NotBlank(message = "{user.password.notBlank}")
        @Size(min = 6, message = "{user.name.size}")
        String password
) {}
