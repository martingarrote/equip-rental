package com.martingarrote.equip_rental.domain.history;

import com.martingarrote.equip_rental.domain.equipment.EquipmentDataProvider;
import com.martingarrote.equip_rental.domain.history.dto.HistoryResponse;
import com.martingarrote.equip_rental.domain.user.UserDataProvider;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;
    private final EquipmentDataProvider equipmentDataProvider;
    private final UserDataProvider userDataProvider;

    @Override
    @Transactional
    public void register(UUID equipmentId, UUID userId, HistoryAction action, String description) {
        var equipment = equipmentDataProvider.getEntityById(equipmentId);
        var user = userDataProvider.getEntityById(userId);

        var entity = HistoryEntity.builder()
                .equipment(equipment)
                .user(user)
                .action(action)
                .timestamp(LocalDateTime.now())
                .description(description)
                .build();

        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<HistoryResponse> list(UUID equipmentId, UUID userId, HistoryAction action, HistoryDetailLevel level, Pageable pageable) {
        var effectiveLevel = Objects.isNull(level) ? HistoryDetailLevel.SUMMARY : level;
        var history = repository.findWithFilters(equipmentId, userId, action, pageable);
        return PageResponse.of(history, effectiveLevel.getLevel());
    }

}
