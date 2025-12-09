package com.martingarrote.equip_rental.domain.rental;

public enum RentalStatus {
    PENDING("Pendente"),
    ACTIVE("Ativo"),
    COMPLETED("Concluído"),
    OVERDUE("Atrasado"),
    CANCELLED("Cancelado"),
    REJECTED("Rejeitado");

    private final String description;

    RentalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
