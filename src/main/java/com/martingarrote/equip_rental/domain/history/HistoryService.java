package com.martingarrote.equip_rental.domain.history;

import com.martingarrote.equip_rental.domain.history.dto.HistoryResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HistoryService {

    void create(UUID equipmentId, UUID user, HistoryAction action, String description);

    PageResponse<HistoryResponse> list(UUID equipmentId, UUID userId, HistoryAction action, HistoryDetailLevel level, Pageable pageable);
}
