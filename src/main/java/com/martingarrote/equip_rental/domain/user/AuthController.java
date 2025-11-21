package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.domain.user.dto.AuthRequest;
import com.martingarrote.equip_rental.domain.user.dto.UserRequest;
import com.martingarrote.equip_rental.domain.user.dto.AuthResponse;
import com.martingarrote.equip_rental.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        var user = service.register(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(service.login(authRequest));
    }

    @PatchMapping("{id}/role")
    public ResponseEntity<UserResponse> changeRole(
            @PathVariable String id,
            @RequestParam Role role
    ) {
        return ResponseEntity.ok(service.changeUserRole(id, role));
    }
}