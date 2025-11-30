package com.martingarrote.equip_rental.domain.history;

import com.martingarrote.equip_rental.domain.history.dto.HistoryResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService service;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponse<HistoryResponse>> list(
            @RequestParam(required = false) UUID equipmentId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) HistoryAction action,
            @RequestParam(required = false) HistoryDetailLevel detailLevel,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.list(equipmentId, userId, action, detailLevel, pageable));
    }

}
