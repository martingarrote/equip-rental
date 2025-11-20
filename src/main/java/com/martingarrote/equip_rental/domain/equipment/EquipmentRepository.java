package com.martingarrote.equip_rental.domain.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface EquipmentRepository extends JpaRepository<EquipmentEntity, UUID> {
}
