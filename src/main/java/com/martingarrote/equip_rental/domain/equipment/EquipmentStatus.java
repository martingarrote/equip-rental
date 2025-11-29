package com.martingarrote.equip_rental.domain.equipment;

public enum EquipmentStatus {
    RESERVED("Reservado"),
    AVAILABLE("Disponível"),
    RENTED("Alugado"),
    NEED_MAINTENANCE("Precisa de manuntenção"),
    MAINTENANCE("Manutenção"),
    INACTIVE("Inativo");

    private final String description;

    EquipmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
