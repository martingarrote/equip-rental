package com.martingarrote.equip_rental.domain.rental;

import com.martingarrote.equip_rental.domain.contract.ContractEntity;
import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Rental")
@Table(name = "rentals")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RentalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotNull(message = "{rental.contract.notNull}")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", nullable = false)
    private ContractEntity contract;

    @NotNull(message = "{rental.equipment.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @NotNull(message = "{rental.customer.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;

    @NotNull(message = "{rental.startDate.notNull}")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "{rental.expectedEndDate.notNull}")
    @Column(name = "expected_end_date", nullable = false)
    private LocalDate expectedEndDate;

    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;

    @NotNull(message = "{rental.status.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RentalStatus status;

    @NotNull(message = "{rental.totalValue.notNull}")
    @DecimalMin(value = "0.00", message = "{rental.totalValue.min}")
    @Column(name = "total_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Size(max = 500, message = "{rental.notes.size}")
    @Column(name = "notes", length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "return_condition", length = 20)
    private ReturnCondition returnCondition;

    @Column(name = "requires_maintenance", nullable = false)
    private Boolean requiresMaintenance = false;

}
