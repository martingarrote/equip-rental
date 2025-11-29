package com.martingarrote.equip_rental.domain.rental;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<RentalEntity, UUID> {
    Page<RentalEntity> findByContractId(UUID contractId, Pageable pageable);

    Page<RentalEntity> findByEquipmentId(UUID equipmentId, Pageable pageable);

    @Query("""
                SELECT r FROM Rental r
                WHERE r.status = 'ACTIVE'
                AND r.expectedEndDate < CURRENT_DATE
            """)
    Page<RentalEntity> findOverdue(Pageable pageable);
}

