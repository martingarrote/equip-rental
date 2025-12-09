package com.martingarrote.equip_rental.domain.contract;

public enum ContractStatus {
    PENDING_APPROVAL("Pendente de aprovação"),
    ACTIVE("Ativo"),
    EXPIRED("Expirado"),
    CANCELLED("Cancelado"),
    COMPLETED("Concluído"),
    REJECTED("Rejeitado");

    private final String description;

    ContractStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
