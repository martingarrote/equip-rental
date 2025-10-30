package com.martingarrote.equip_rental.domain.equipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface EquipmentRepository extends JpaRepository<EquipmentEntity, UUID> {
}
