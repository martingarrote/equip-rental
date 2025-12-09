package com.martingarrote.equip_rental.domain.rental;

import com.martingarrote.equip_rental.domain.rental.dto.RentalResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService service;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> retrieve(@PathVariable UUID id) {
        return ResponseEntity.ok(service.retrieve(id));
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<PageResponse<RentalResponse>> findByContract(@PathVariable UUID contractId, Pageable pageable) {
        return ResponseEntity.ok(service.findByContract(contractId, pageable));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<PageResponse<RentalResponse>> findByEquipment(@PathVariable UUID equipmentId, Pageable pageable) {
        return ResponseEntity.ok(service.findByEquipment(equipmentId, pageable));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/overdue")
    public ResponseEntity<PageResponse<RentalResponse>> findOverdue(Pageable pageable) {
        return ResponseEntity.ok(service.findOverdue(pageable));
    }
}
