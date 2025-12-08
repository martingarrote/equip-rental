package com.martingarrote.equip_rental.domain.equipment;

import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentDashboardResponse;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentRequest;
import com.martingarrote.equip_rental.domain.equipment.dto.EquipmentResponse;
import com.martingarrote.equip_rental.domain.history.HistoryAction;
import com.martingarrote.equip_rental.domain.history.HistoryService;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import com.martingarrote.equip_rental.infrastructure.security.SecurityUtils;
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
    private final EquipmentDataProvider dataProvider;

    private final SecurityUtils securityUtils;
    private final HistoryService historyService;

    private static final Integer STANDARD_LEAD_TIME = 7;

    @Transactional
    @Override
    public EquipmentResponse create(EquipmentRequest request) {
        if (repository.existsBySerialNumber(request.serialNumber())) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_SERIAL_NUMBER_ALREADY_IN_USE);
        }

        var saved = repository.save(EquipmentEntity.fromRequest(request));

        historyService.register(
                saved.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.CREATED,
                "Novo equipamento criado"
        );

        return EquipmentResponse.full(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentResponse retrieve(UUID id) {
        return EquipmentResponse.full(dataProvider.getEntityById(id));
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

    @Override
    @Transactional(readOnly = true)
    public EquipmentDashboardResponse getDashboard(EquipmentStatus status, EquipmentType type, Pageable pageable) {
        var stats = repository.getStatsByType(type);
        var page = PageResponse.of(
                repository.findByStatusAndType(status, type, pageable),
                equipment -> EquipmentResponse.summary(equipment).withId(equipment.getId())
        );

        return new EquipmentDashboardResponse(page, stats);
    }

    @Transactional
    @Override
    public EquipmentResponse update(UUID id, EquipmentRequest request) {
        var entityToUpdate = dataProvider.getEntityById(id);

        if (entityToUpdate.getSerialNumber().equals(request.serialNumber()) && repository.existsBySerialNumber(request.serialNumber())) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_SERIAL_NUMBER_ALREADY_IN_USE);
        }

        var updatedEntity = EquipmentEntity.fromRequest(request);
        updatedEntity.setId(entityToUpdate.getId());

        historyService.register(
                updatedEntity.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.UPDATED,
                "Equipamento atualizado"
        );

        return EquipmentResponse.full(repository.save(updatedEntity));
    }

    @Transactional
    @Override
    public EquipmentEntity reserve(UUID equipmentId) {
        EquipmentEntity equipment = dataProvider.getEntityById(equipmentId);

        if (!equipment.getStatus().equals(EquipmentStatus.AVAILABLE)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_UNAVAILABLE);
        }

        equipment.setStatus(EquipmentStatus.RESERVED);

        historyService.register(
                equipment.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.RESERVED,
                "Equipamento reservado"
        );

        return repository.save(equipment);
    }


    @Transactional
    @Override
    public EquipmentEntity rent(UUID equipmentId, boolean requestedByCustomer) {
        EquipmentEntity equipment = dataProvider.getEntityById(equipmentId);

        var expectedStatus = requestedByCustomer ? EquipmentStatus.RESERVED : EquipmentStatus.AVAILABLE;

        if (!equipment.getStatus().equals(expectedStatus)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_UNAVAILABLE);
        }

        equipment.setStatus(EquipmentStatus.RENTED);

        historyService.register(
                equipment.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.RENTED,
                "Equipamento locado"
        );

        return repository.save(equipment);
    }

    @Transactional
    @Override
    public EquipmentEntity release(UUID equipmentId) {
        EquipmentEntity equipment = dataProvider.getEntityById(equipmentId);

        if (equipment.getStatus().equals(EquipmentStatus.AVAILABLE)) {
            return equipment;
        }

        equipment.setStatus(EquipmentStatus.AVAILABLE);

        historyService.register(
                equipment.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.RELEASED,
                "Equipamento liberado"
        );

        return repository.save(equipment);
    }

    @Transactional
    @Override
    public EquipmentEntity sendToMaintenance(UUID equipmentId) {
        EquipmentEntity equipment = dataProvider.getEntityById(equipmentId);

        equipment.setStatus(EquipmentStatus.NEED_MAINTENANCE);

        historyService.register(
                equipment.getId(),
                securityUtils.getCurrentUserId(),
                HistoryAction.MAINTENANCE_REQUESTED,
                "Equipamento colocado na fila para manutenção"
        );

        return repository.save(equipment);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorMessage.EQUIPMENT_NOT_FOUND);
        }

        historyService.register(
                id,
                securityUtils.getCurrentUserId(),
                HistoryAction.DELETED,
                "Equipamento excluido"
        );

        repository.deleteById(id);
    }
}
