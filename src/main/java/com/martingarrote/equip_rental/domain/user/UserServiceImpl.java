package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.domain.user.dto.AuthRequest;
import com.martingarrote.equip_rental.domain.user.dto.UserRequest;
import com.martingarrote.equip_rental.domain.user.dto.AuthResponse;
import com.martingarrote.equip_rental.domain.user.dto.UserResponse;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        var user = repository.findByEmail(request.email())
                .orElseThrow(() -> new ServiceException(ErrorMessage.OBJECT_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ServiceException(ErrorMessage.BAD_CREDENTIALS);
        }

        return AuthResponse.logged(tokenProvider.generateToken(user));
    }

    @Override
    @Transactional
    public UserResponse changeUserRole(String userId, Role newRole) {
        var user = repository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ServiceException(ErrorMessage.OBJECT_NOT_FOUND));

        if (user.getRole().equals(newRole)) {
            return UserResponse.detailed(user);
        }

        user.setRole(newRole);
        var updatedUser = repository.save(user);

        return UserResponse.detailed(updatedUser);
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new ServiceException(ErrorMessage.USER_ALREADY_EXISTS);
        }

        var user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(Role.CUSTOMER)
                .active(true)
                .build();

        return UserResponse.summary(repository.save(user));
    }
}
