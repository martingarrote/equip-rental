package com.martingarrote.equip_rental.domain.equipment.dto;

public record EquipmentStatsResponse(
        Long total,
        Long available,
        Long reserved,
        Long rented
) {
}
