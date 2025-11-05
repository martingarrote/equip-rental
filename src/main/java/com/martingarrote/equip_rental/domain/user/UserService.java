package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.domain.user.request.AuthRequest;
import com.martingarrote.equip_rental.domain.user.response.AuthResponse;

import com.martingarrote.equip_rental.domain.user.request.UserRequest;
import com.martingarrote.equip_rental.domain.user.response.UserResponse;

public interface UserService {
    AuthResponse login(AuthRequest request);
    UserResponse register(UserRequest request);
    UserResponse changeUserRole(String userId, Role newRole);
}
