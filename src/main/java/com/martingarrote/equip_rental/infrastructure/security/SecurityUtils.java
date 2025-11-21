package com.martingarrote.equip_rental.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public String getCurrentUserEmail(UserDetails userDetails) {
        return userDetails.getUsername();
    }
}