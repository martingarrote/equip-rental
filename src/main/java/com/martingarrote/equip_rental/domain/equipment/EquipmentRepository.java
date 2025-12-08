package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentStatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
interface EquipmentRepository extends JpaRepository<EquipmentEntity, UUID> {
    @Query("""
                    SELECT e FROM Equipment e
                    WHERE (:status IS NULL OR e.status = :status)
                      AND (:type IS NULL OR e.type = :type)
            """)
    Page<EquipmentEntity> findByStatusAndType(
            @Param("status") EquipmentStatus status,
            @Param("type") EquipmentType type,
            Pageable pageable
    );


    @Query("""
            SELECT new com.martingarrote.equip_rental.domain.equipment.dto.EquipmentStatsResponse(
                COUNT(e),
                SUM(CASE WHEN e.status = 'AVAILABLE' THEN 1 ELSE 0 END),
                SUM(CASE WHEN e.status = 'RESERVED' THEN 1 ELSE 0 END),
                SUM(CASE WHEN e.status = 'RENTED' THEN 1 ELSE 0 END)
            )
            FROM Equipment e
            WHERE (:type IS NULL OR e.type = :type)
    """)
    EquipmentStatsResponse getStatsByType(@Param("type") EquipmentType type);

    @Query("SELECT DISTINCT e FROM Equipment e " +
           "JOIN Rental r ON r.equipment.id = e.id " +
           "WHERE r.customer.id = :customerId AND r.status = 'ACTIVE'")
    Page<EquipmentEntity> findByCurrentCustomer(@Param("customerId") UUID customerId, Pageable pageable);

    Page<EquipmentEntity> findByNextPreventiveMaintenanceLessThanEqual(LocalDate date, Pageable pageable);

    boolean existsBySerialNumber(String serialNumber);
}
