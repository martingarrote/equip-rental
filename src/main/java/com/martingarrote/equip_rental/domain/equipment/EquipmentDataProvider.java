package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentDataProvider {

    private final EquipmentRepository repository;

    public EquipmentEntity getEntityById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );
    }

}
