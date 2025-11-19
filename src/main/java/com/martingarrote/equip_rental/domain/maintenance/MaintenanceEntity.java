package com.martingarrote.equip_rental.domain.maintenance;

import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Maintenance")
@Table(name = "maintenances")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class MaintenanceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotNull(message = "{maintenance.equipment.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @NotNull(message = "{maintenance.responsible.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id", nullable = false)
    private UserEntity responsible;

    @NotNull(message = "{maintenance.type.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private MaintenanceType type;

    @NotNull(message = "{maintenance.status.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MaintenanceStatus status;

    @Size(max = 500, message = "{maintenance.description.size}")
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Size(max = 1000, message = "{maintenance.notes.size}")
    @Column(name = "notes", length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private UserEntity approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "requires_approval", nullable = false)
    private Boolean requiresApproval = false;

}
