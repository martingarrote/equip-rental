package com.martingarrote.equip_rental.domain.history;

public enum HistoryAction {
    CREATED("Criado"),
    UPDATED("Atualizado"),
    DELETED("Deletado"),

    RENTED("Alugado"),
    RESERVED("Reservado"),
    RELEASED("Liberado"),

    MAINTENANCE_REQUESTED("Solicitada manutenção"),
    MAINTENANCE_STARTED("Manutenção iniciada"),
    MAINTENANCE_COMPLETED("Manutenção concluída"),

    CONTRACT_CREATED("Contrato criado"),
    CONTRACT_REQUESTED("Contrato solicitado"),
    CONTRACT_APPROVED("Contrato aprovado"),
    CONTRACT_REJECTED("Contrato rejeitado"),
    CONTRACT_CANCELLED("Contrato cancelado");

    private final String description;

    HistoryAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
