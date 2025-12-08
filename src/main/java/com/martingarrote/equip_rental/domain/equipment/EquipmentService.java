package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentDashboardResponse;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentRequest;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EquipmentService {
    EquipmentResponse create(EquipmentRequest request);
    EquipmentResponse retrieve(UUID id);
    PageResponse<EquipmentResponse> list(EquipmentStatus status, EquipmentType type, Pageable pageable);
    PageResponse<EquipmentResponse> findByCustomer(UUID customer, Pageable pageable);
    PageResponse<EquipmentResponse> findNeedingMaintenance(Pageable pageable);
    EquipmentDashboardResponse getDashboard(EquipmentStatus status, EquipmentType type, Pageable pageable);
    EquipmentResponse update(UUID id, EquipmentRequest request);
    EquipmentEntity reserve(UUID equipmentId);
    EquipmentEntity rent(UUID equipmentId, boolean requestedByCustomer);
    EquipmentEntity release(UUID equipmentId);
    EquipmentEntity sendToMaintenance(UUID equipmentId);
    void delete(UUID id);
}
