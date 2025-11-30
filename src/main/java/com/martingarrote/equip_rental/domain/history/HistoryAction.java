package com.martingarrote.equip_rental.domain.history;

public enum HistoryAction {
    CREATED,
    UPDATED,
    RENTED,
    DELIVERED,
    RETURNED,
    MAINTENANCE_STARTED,
    MAINTENANCE_COMPLETED,
    STATUS_CHANGED,
    CONTRACT_APPROVED,
    CONTRACT_REJECTED,
    CONTRACT_CANCELLED
}
