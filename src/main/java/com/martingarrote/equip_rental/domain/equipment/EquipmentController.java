package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentDashboardResponse;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentRequest;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping
    public ResponseEntity<EquipmentResponse> create(@Valid @RequestBody EquipmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponse> retrieve(@PathVariable UUID id) {
        return ResponseEntity.ok(service.retrieve(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<EquipmentResponse>> list(
            @RequestParam(required = false) EquipmentStatus status,
            @RequestParam(required = false) EquipmentType type,
            Pageable pageable) {
        return ResponseEntity.ok(service.list(status, type, pageable));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<EquipmentDashboardResponse> getDashboard(
            @RequestParam(required = false) EquipmentStatus status,
            @RequestParam(required = false) EquipmentType type,
            Pageable pageable) {
        return ResponseEntity.ok(service.getDashboard(status, type, pageable));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<PageResponse<EquipmentResponse>> findByCustomer(
            @PathVariable UUID customerId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByCustomer(customerId, pageable));
    }

    @GetMapping("/maintenance/needed")
    public ResponseEntity<PageResponse<EquipmentResponse>> findNeedingMaintenance(
            Pageable pageable) {
        return ResponseEntity.ok(service.findNeedingMaintenance(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
