package com.martingarrote.equip_rental.domain.history;

import com.martingarrote.equip_rental.domain.history.dto.HistoryResponse;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum HistoryDetailLevel {
    FULL(HistoryResponse::full),
    DETAILED(HistoryResponse::detailed),
    SUMMARY(HistoryResponse::summary);

    private final Function<HistoryEntity, HistoryResponse> level;

    HistoryDetailLevel(Function<HistoryEntity, HistoryResponse> level) {
        this.level = level;
    }

}
