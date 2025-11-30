package com.martingarrote.equip_rental.domain.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface HistoryRepository extends JpaRepository<HistoryEntity, UUID> {
    @Query("""
                    SELECT h FROM History h
                    WHERE (:equipmentId IS NULL OR h.equipment.id = :equipmentId)
                        AND (:userId IS NULL OR h.user.id = :userId)
                        AND (:action IS NULL OR h.action = :action)
            """)
    Page<HistoryEntity> findWithFilters(
            @Param("equipmentId") UUID equipmentId,
            @Param("userId") UUID userId,
            @Param("action") HistoryAction action,
            Pageable pageable
    );
}
