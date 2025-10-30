package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Equipment")
@Table(name = "equipments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id", "serialNumber"}, callSuper = false)
public class EquipmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotBlank(message = "{equipment.name.notBlank}")
    @Size(min = 2, max = 100, message = "{equipment.name.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull(message = "{equipment.type.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private EquipmentType type;

    @NotBlank(message = "{equipment.serialNumber.notBlank}")
    @Size(min = 3, max = 50, message = "{equipment.serialNumber.size}")
    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @NotNull(message = "{equipment.status.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EquipmentStatus status;

    @NotNull(message = "{equipment.acquisitionDate.notNull}")
    @PastOrPresent(message = "{equipment.acquisitionDate.pastOrPresent}")
    @Column(name = "acquisition_date", nullable = false)
    private LocalDate acquisitionDate;

    @NotNull(message = "{equipment.acquisitionValue.notNull}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{equipment.acquisitionValue.positive}")
    @Digits(integer = 10, fraction = 2, message = "{equipment.acquisitionValue.format}")
    @Column(name = "acquisition_value", nullable = false, precision = 12, scale = 2)
    private BigDecimal acquisitionValue;

    @FutureOrPresent(message = "{equipment.nextMaintenance.futureOrPresent}")
    @Column(name = "next_preventive_maintenance")
    private LocalDate nextPreventiveMaintenance;

    @Min(value = 1, message = "{equipment.maintenancePeriodDays.min}")
    @Max(value = 1095, message = "{equipment.maintenancePeriodDays.max}")
    @Column(name = "maintenance_period_days")
    private Integer maintenancePeriodDays;

    @Size(max = 500, message = "{equipment.notes.size}")
    @Column(name = "notes", length = 500)
    private String notes;
}
