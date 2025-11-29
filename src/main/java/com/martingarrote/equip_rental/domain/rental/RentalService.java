package com.martingarrote.equip_rental.domain.rental;

import com.martingarrote.equip_rental.domain.rental.dto.RentalResponse;
import com.martingarrote.equip_rental.domain.rental.dto.ReturnEquipmentRequest;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RentalService {

    RentalResponse retrieve(UUID id);
    PageResponse<RentalResponse> findByContract(UUID contractId, Pageable pageable);
    PageResponse<RentalResponse> findByEquipment(UUID equipmentId, Pageable pageable);
    PageResponse<RentalResponse> findOverdue(Pageable pageable);
    void returnEquipment(UUID rentalId, ReturnEquipmentRequest request);

}
