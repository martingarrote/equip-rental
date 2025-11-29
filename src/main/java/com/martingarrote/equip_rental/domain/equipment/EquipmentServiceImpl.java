package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentRequest;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository repository;

    private static final Integer STANDARD_LEAD_TIME = 7;

    @Transactional
    @Override
    public EquipmentResponse create(EquipmentRequest request) {
        if (repository.existsBySerialNumber(request.serialNumber())) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_SERIAL_NUMBER_ALREADY_IN_USE);
        }

        var saved = repository.save(EquipmentEntity.fromRequest(request));

        return EquipmentResponse.full(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentResponse retrieve(UUID id) {
        EquipmentEntity equipment = repository.findById(id).orElseThrow(
            () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );

        return EquipmentResponse.full(equipment);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<EquipmentResponse> list(EquipmentStatus status, EquipmentType type, Pageable pageable) {
        Page<EquipmentEntity> filteredEquipments = repository.findByStatusAndType(status, type, pageable);

        return PageResponse.of(
                filteredEquipments,
                equipment -> EquipmentResponse.summary(equipment).withId(equipment.getId())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<EquipmentResponse> findByCustomer(UUID customer, Pageable pageable) {
        Page<EquipmentEntity> filteredEquipments = repository.findByCurrentCustomer(customer, pageable);

        return PageResponse.of(
                filteredEquipments,
                equipment -> EquipmentResponse.summary(equipment).withId(equipment.getId())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<EquipmentResponse> findNeedingMaintenance(Pageable pageable) {
        LocalDate alertDate = LocalDate.now().plusDays(STANDARD_LEAD_TIME);
        Page<EquipmentEntity> filteredEquipments = repository.findByNextPreventiveMaintenanceLessThanEqual(alertDate, pageable);

        return PageResponse.of(
                filteredEquipments,
                equipment -> EquipmentResponse.summary(equipment).withId(equipment.getId())
        );
    }

    @Transactional
    @Override
    public EquipmentResponse update(UUID id, EquipmentRequest request) {
        var entityToUpdate = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );

        if (entityToUpdate.getSerialNumber().equals(request.serialNumber()) && repository.existsBySerialNumber(request.serialNumber())) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_SERIAL_NUMBER_ALREADY_IN_USE);
        }

        var updatedEntity = EquipmentEntity.fromRequest(request);
        updatedEntity.setId(entityToUpdate.getId());

        return EquipmentResponse.full(repository.save(updatedEntity));
    }

    @Transactional
    @Override
    public EquipmentEntity reserve(UUID equipmentId) {
        EquipmentEntity equipment = repository.findById(equipmentId).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );

        if (!equipment.getStatus().equals(EquipmentStatus.AVAILABLE)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_UNAVAILABLE);
        }

        equipment.setStatus(EquipmentStatus.RESERVED);

        return repository.save(equipment);
    }


    @Transactional
    @Override
    public EquipmentEntity rent(UUID equipmentId, boolean requestedByCustomer) {
        EquipmentEntity equipment = repository.findById(equipmentId).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );

        var expectedStatus = requestedByCustomer ? EquipmentStatus.RESERVED : EquipmentStatus.AVAILABLE;

        if (!equipment.getStatus().equals(expectedStatus)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_UNAVAILABLE);
        }

        equipment.setStatus(EquipmentStatus.RENTED);

        return repository.save(equipment);
    }

    @Transactional
    @Override
    public EquipmentEntity release(UUID equipmentId) {
        EquipmentEntity equipment = repository.findById(equipmentId).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );

        if (equipment.getStatus().equals(EquipmentStatus.AVAILABLE)) {
            return equipment;
        }

        equipment.setStatus(EquipmentStatus.AVAILABLE);

        return repository.save(equipment);
    }

    @Transactional
    @Override
    public EquipmentEntity sendToMaintenance(UUID equipmentId) {
        var equipment = repository.findById(equipmentId).orElseThrow(
                () -> new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND)
        );
        equipment.setStatus(EquipmentStatus.NEED_MAINTENANCE);
        return repository.save(equipment);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND);
        }

        repository.deleteById(id);
    }
}
