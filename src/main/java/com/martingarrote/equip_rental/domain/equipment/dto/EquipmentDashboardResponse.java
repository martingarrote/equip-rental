package com.martingarrote.equip_rental.domain.equipment.dto;

import com.martingarrote.equip_rental.infrastructure.response.PageResponse;

public record EquipmentDashboardResponse(
        PageResponse<EquipmentResponse> equipments,
        EquipmentStatsResponse stats
) {
}
