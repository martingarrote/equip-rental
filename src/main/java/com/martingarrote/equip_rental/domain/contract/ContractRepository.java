package com.martingarrote.equip_rental.domain.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {

    @Query("""
                    SELECT c FROM Contract c
                    WHERE (:status IS NULL OR c.status = :status)
                      AND (:customerId IS NULL OR c.customer.id = :customerId)
            """)
    Page<ContractEntity> findByStatusAndCustomerId(
            @Param("status") ContractStatus status,
            @Param("customerId") UUID customerId,
            Pageable pageable
    );

    @Query("""
                    SELECT c FROM Contract c
                    WHERE (:status IS NULL OR c.status = :status)
                      AND (:customerEmail = c.customer.email)
            """)
    Page<ContractEntity> findByStatusAndCustomerEmail(
            @Param("status") ContractStatus status,
            @Param("customerEmail") String customerEmail,
            Pageable pageable
    );

    @Query("""
                SELECT c FROM Contract c
                WHERE c.status = 'ACTIVE'
                AND c.endDate BETWEEN :startDate AND :endDate
                ORDER BY c.endDate ASC
            """)
    Page<ContractEntity> findExpiring(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    @Query("""
                SELECT c FROM Contract c
                WHERE c.status = 'PENDING_APPROVAL'
            """)
    Page<ContractEntity> findPendingApproval(Pageable pageable);

    Optional<ContractEntity> findByIdAndCustomerEmail(UUID id, String email);
}
