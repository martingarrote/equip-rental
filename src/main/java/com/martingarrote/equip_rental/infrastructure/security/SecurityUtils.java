package com.martingarrote.equip_rental.infrastructure.security;

import com.martingarrote.equip_rental.domain.user.UserService;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserService userService;

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ServiceException(ErrorMessage.UNAUTHORIZED);
        }

        return authentication.getName();
    }

    public UUID getCurrentUserId() {
        var email = getCurrentUserEmail();
        return userService.findEntityByEmail(email).getId();
    }
}