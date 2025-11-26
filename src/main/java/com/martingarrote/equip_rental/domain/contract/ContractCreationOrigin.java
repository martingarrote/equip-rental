package com.martingarrote.equip_rental.domain.contract;

import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.equipment.EquipmentService;
import com.martingarrote.equip_rental.domain.rental.RentalStatus;

import java.util.UUID;
import java.util.function.BiFunction;

public enum ContractCreationOrigin {
    CUSTOMER(
            ContractStatus.PENDING_APPROVAL,
            RentalStatus.PENDING,
            EquipmentService::reserve
    ),
    EMPLOYEE(
            ContractStatus.ACTIVE,
            RentalStatus.ACTIVE,
            (equipmentService, equipmentId) -> equipmentService.rent(equipmentId, false)
    );

    private final ContractStatus contractStatus;
    private final RentalStatus rentalStatus;
    private final BiFunction<EquipmentService, UUID, EquipmentEntity> equipmentAction;

    ContractCreationOrigin(
            ContractStatus contractStatus,
            RentalStatus rentalStatus,
            BiFunction<EquipmentService, UUID, EquipmentEntity> equipmentAction
    ) {
        this.contractStatus = contractStatus;
        this.rentalStatus = rentalStatus;
        this.equipmentAction = equipmentAction;
    }

    public ContractStatus getContractStatus() { return contractStatus; }
    public RentalStatus getRentalStatus() { return rentalStatus; }
    public EquipmentEntity applyEquipmentAction(EquipmentService service, UUID id) {
        return equipmentAction.apply(service, id);
    }
}
