package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.domain.user.dto.AuthRequest;
import com.martingarrote.equip_rental.domain.user.dto.AuthResponse;

import com.martingarrote.equip_rental.domain.user.dto.UserRequest;
import com.martingarrote.equip_rental.domain.user.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    AuthResponse login(AuthRequest request);
    UserResponse register(UserRequest request);
    UserResponse changeUserRole(String userId, Role newRole);
    UserEntity findEntityByEmail(String email);
    UserEntity findEntityById(UUID id);
}
