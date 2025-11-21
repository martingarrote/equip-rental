package com.martingarrote.equip_rental.domain.contract;

import com.martingarrote.equip_rental.domain.equipment.EquipmentStatus;
import com.martingarrote.equip_rental.domain.rental.RentalEntity;
import com.martingarrote.equip_rental.domain.rental.RentalStatus;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Contract")
@Table(name = "contracts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = {"id", "number"}, callSuper = false)
public class ContractEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @NotNull(message = "{contract.customer.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RentalEntity> rentals = new ArrayList<>();

    @SequenceGenerator(name = "contract_number_seq", sequenceName = "contract_number_seq", allocationSize = 1)
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull(message = "{contract.startDate.notNull}")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "{contract.endDate.notNull}")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "{contract.status.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ContractStatus status;

    @NotNull(message = "{contract.totalValue.notNull}")
    @DecimalMin(value = "0.00", message = "{contract.totalValue.min}")
    @Column(name = "total_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Size(max = 500, message = "{contract.notes.size}")
    @Column(name = "notes", length = 500)
    private String notes;

    public void addRental(RentalEntity rental) {
        rentals.add(rental);
        rental.setContract(this);
        rental.setCustomer(this.customer);

        if (this.status == ContractStatus.PENDING_APPROVAL) {
            rental.setStatus(RentalStatus.PENDING);
            rental.getEquipment().setStatus(EquipmentStatus.RESERVED);
        } else if (this.status == ContractStatus.ACTIVE) {
            rental.setStatus(RentalStatus.ACTIVE);
            rental.getEquipment().setStatus(EquipmentStatus.RENTED);
        }
    }
}
