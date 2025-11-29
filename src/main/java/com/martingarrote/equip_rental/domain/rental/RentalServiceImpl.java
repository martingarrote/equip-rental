package com.martingarrote.equip_rental.domain.rental;

import com.martingarrote.equip_rental.domain.equipment.EquipmentService;
import com.martingarrote.equip_rental.domain.rental.dto.RentalResponse;
import com.martingarrote.equip_rental.domain.rental.dto.ReturnEquipmentRequest;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository repository;
    private final EquipmentService equipmentService;

    @Override
    @Transactional(readOnly = true)
    public RentalResponse retrieve(UUID id) {
        var entity = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.RENTAL_NOT_FOUND)
        );
        return RentalResponse.full(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RentalResponse> findByContract(UUID contractId, Pageable pageable) {
        var rentals = repository.findByContractId(contractId, pageable);
        return PageResponse.of(rentals, RentalResponse::summary);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RentalResponse> findByEquipment(UUID equipmentId, Pageable pageable) {
        var rentals = repository.findByEquipmentId(equipmentId, pageable);
        return PageResponse.of(rentals, RentalResponse::summary);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RentalResponse> findOverdue(Pageable pageable) {
        var rentals = repository.findOverdue(pageable);
        return PageResponse.of(rentals, RentalResponse::summary);
    }

    @Override
    @Transactional
    public void returnEquipment(UUID rentalId, ReturnEquipmentRequest request) {
        var rental = repository.findById(rentalId).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );
        rental.setReturnCondition(request.returnCondition());
        rental.setRequiresMaintenance(request.requiresMaintenance());

        if (request.returnCondition().equals(ReturnCondition.DAMAGED)) {
            equipmentService.sendToMaintenance(rental.getEquipment().getId());
        }

        repository.save(rental);
    }
}
