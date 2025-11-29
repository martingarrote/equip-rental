package com.martingarrote.equip_rental.domain.contract;

import com.martingarrote.equip_rental.domain.contract.dto.ContractActionRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import com.martingarrote.equip_rental.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;
    private final SecurityUtils securityUtils;

    @PostMapping("/request")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ContractResponse> requestContract(@Valid @RequestBody ContractRequest request) {
        String customerEmail = securityUtils.getCurrentUserEmail();
        ContractResponse contract = service.createByCustomer(request, customerEmail);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contract.id())
                .toUri();

        return ResponseEntity.created(location).body(contract);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ContractResponse> createContract(@Valid @RequestBody ContractRequest request) {
        ContractResponse contract = service.createByEmployee(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contract.id())
                .toUri();

        return ResponseEntity.created(location).body(contract);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ContractResponse> retrieve(@PathVariable UUID id) {
        return ResponseEntity.ok(service.retrieve(id));
    }


    @GetMapping("/my-contracts/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ContractResponse> retrieveByCustomer(@PathVariable UUID id) {
        String customerEmail = securityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(service.retrieveByCustomer(id, customerEmail));
    }

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PageResponse<ContractResponse>> listAll(
            @RequestParam(required = false) ContractStatus status,
            @RequestParam(required = false) UUID customerId,
            Pageable pageable) {
        return ResponseEntity.ok(service.list(status, customerId, pageable));
    }

    @GetMapping("/my-contracts")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PageResponse<ContractResponse>> listMyContracts(
            @RequestParam(required = false) ContractStatus status,
            Pageable pageable
    ) {
        String customerEmail = securityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(service.listByCustomer(customerEmail, status, pageable));
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PageResponse<ContractResponse>> findExpiring(
            @RequestParam(required = false) Integer daysAhead,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findExpiring(daysAhead, pageable));
    }

    @GetMapping("/pending_approval")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<PageResponse<ContractResponse>> findPendingApproval(Pageable pageable) {
        return ResponseEntity.ok(service.findPendingApproval(pageable));
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> approve(@PathVariable UUID id) {
        service.approve(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> reprove(@PathVariable UUID id, @RequestBody @Valid ContractActionRequest request) {
        service.reject(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> cancel(@PathVariable UUID id,@RequestBody @Valid ContractActionRequest request) {
        String userEmail = securityUtils.getCurrentUserEmail();
        service.cancel(id, userEmail, request);
        return ResponseEntity.noContent().build();
    }
}
