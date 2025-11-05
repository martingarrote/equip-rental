package com.martingarrote.equip_rental.domain.user.response;

public record AuthResponse(
        String accessToken
) {

    public static AuthResponse logged(String token) {
        return new AuthResponse(token);
    }
}