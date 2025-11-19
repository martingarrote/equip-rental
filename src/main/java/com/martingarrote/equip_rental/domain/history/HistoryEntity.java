package com.martingarrote.equip_rental.domain.history;

import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "History")
@Table(name = "histories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class HistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotNull(message = "{history.equipment.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @NotNull(message = "{history.user.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull(message = "{history.action.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 30)
    private HistoryAction action;

    @NotNull(message = "{history.timestamp.notNull}")
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Size(max = 500, message = "{history.description.size}")
    @Column(name = "description", length = 500)
    private String description;

}
