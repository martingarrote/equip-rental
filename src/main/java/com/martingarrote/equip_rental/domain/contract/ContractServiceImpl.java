package com.martingarrote.equip_rental.domain.contract;

import com.martingarrote.equip_rental.domain.contract.dto.ContractActionRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractResponse;
import com.martingarrote.equip_rental.domain.equipment.EquipmentEntity;
import com.martingarrote.equip_rental.domain.equipment.EquipmentService;
import com.martingarrote.equip_rental.domain.equipment.EquipmentStatus;
import com.martingarrote.equip_rental.domain.history.HistoryAction;
import com.martingarrote.equip_rental.domain.history.HistoryService;
import com.martingarrote.equip_rental.domain.rental.RentalEntity;
import com.martingarrote.equip_rental.domain.rental.RentalStatus;
import com.martingarrote.equip_rental.domain.rental.dto.RentalItemRequest;
import com.martingarrote.equip_rental.domain.user.UserDataProvider;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import com.martingarrote.equip_rental.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;
    private final UserDataProvider userDataProvider;
    private final EquipmentService equipmentService;

    private final SecurityUtils securityUtils;
    private final HistoryService historyService;

    @Transactional
    @Override
    public ContractResponse createByCustomer(ContractRequest request, String customerEmail) {
        UserEntity customer = userDataProvider.getEntityByEmail(customerEmail);
        ContractEntity contract = createContractEntity(request, customer, ContractCreationOrigin.CUSTOMER);
        return ContractResponse.detailed(repository.save(contract));
    }

    @Transactional
    @Override
    public ContractResponse createByEmployee(ContractRequest request) {
        if (Objects.isNull(request.customerId())) {
            throw new ServiceException(ErrorMessage.CONTRACT_CUSTOMER_ID_REQUIRED);
        }

        UserEntity customer = userDataProvider.getEntityById(request.customerId());
        ContractEntity contract = createContractEntity(request, customer, ContractCreationOrigin.EMPLOYEE);
        return ContractResponse.detailed(repository.save(contract));
    }

    @Transactional(readOnly = true)
    @Override
    public ContractResponse retrieve(UUID id) {
        var contract = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );
        return ContractResponse.detailed(contract);
    }

    @Override
    public ContractResponse retrieveByCustomer(UUID id, String customerEmail) {
        var contract = repository.findByIdAndCustomerEmail(id, customerEmail).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );
        return ContractResponse.detailed(contract);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ContractResponse> list(ContractStatus status, UUID customerId, Pageable pageable) {
        ContractStatus effectiveStatus = Objects.isNull(status) ? ContractStatus.ACTIVE : status;
        Page<ContractEntity> filteredContracts = repository.findByStatusAndCustomerId(effectiveStatus, customerId, pageable);
        return PageResponse.of(
                filteredContracts,
                contract -> ContractResponse.summary(contract).withId(contract.getId())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ContractResponse> listByCustomer(String customerEmail, ContractStatus status, Pageable pageable) {
        ContractStatus effectiveStatus = Objects.isNull(status) ? ContractStatus.ACTIVE : status;
        Page<ContractEntity> filteredContracts = repository.findByStatusAndCustomerEmail(effectiveStatus, customerEmail, pageable);
        return PageResponse.of(
                filteredContracts,
                contract -> ContractResponse.summary(contract).withId(contract.getId())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ContractResponse> findExpiring(Integer daysAhead, Pageable pageable) {
        Integer effectiveDaysAhead = Objects.isNull(daysAhead) ? 7 : daysAhead;
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(effectiveDaysAhead);
        Page<ContractEntity> expiringContracts = repository.findExpiring(today, futureDate, pageable);
        return PageResponse.of(
                expiringContracts,
                contract -> ContractResponse.summary(contract).withId(contract.getId())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ContractResponse> findPendingApproval(Pageable pageable) {
        Page<ContractEntity> filteredContracts = repository.findPendingApproval(pageable);
        return PageResponse.of(
                filteredContracts,
                contract -> ContractResponse.summary(contract).withId(contract.getId())
        );
    }

    @Transactional
    @Override
    public void approve(UUID id) {
        ContractEntity contract = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );

        if (!contract.getStatus().equals(ContractStatus.PENDING_APPROVAL)) {
            throw new ServiceException(ErrorMessage.CONTRACT_ALREADY_RESOLVED); // contrato deve estar pendente de parovação para ser aprovado
        }

        contract.setStatus(ContractStatus.ACTIVE);
        contract.getRentals().forEach(rental -> {
            rental.setStatus(RentalStatus.ACTIVE);
            equipmentService.rent(rental.getEquipment().getId(), true);

            historyService.register(
                    rental.getEquipment().getId(),
                    securityUtils.getCurrentUserId(),
                    HistoryAction.CONTRACT_APPROVED,
                    "Contrato com o equipamento foi aprovado"
            );
        });

        repository.save(contract);
    }

    @Transactional
    @Override
    public void reject(UUID id, ContractActionRequest request) {
        ContractEntity contract = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );

        if (!contract.getStatus().equals(ContractStatus.PENDING_APPROVAL)) {
            throw new ServiceException(ErrorMessage.CONTRACT_ALREADY_RESOLVED); // contrato deve estar pendente de aprovação para ser reprovado
        }

        contract.setStatus(ContractStatus.REJECTED);
        contract.getRentals().forEach(rental -> {
            rental.setStatus(RentalStatus.REJECTED);
            equipmentService.release(rental.getEquipment().getId());

            historyService.register(
                    rental.getEquipment().getId(),
                    securityUtils.getCurrentUserId(),
                    HistoryAction.CONTRACT_REJECTED,
                    String.format("Contrato com o equipamento rejeitado com motivo: %s", request.reason())
            );
        });

        repository.save(contract);
    }

    @Override
    public void cancel(UUID id, String requesterEmail, ContractActionRequest request) {
        ContractEntity contract = repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.CONTRACT_NOT_FOUND)
        );

        if (!contract.getStatus().equals(ContractStatus.ACTIVE)) {
            throw new ServiceException(ErrorMessage.CONTRACT_CANCELLATION_FORBIDDEN);
        }

        contract.setStatus(ContractStatus.CANCELLED);
        contract.getRentals().forEach(rental -> {
            rental.setStatus(RentalStatus.CANCELLED);
            equipmentService.release(rental.getEquipment().getId());

            historyService.register(
                    rental.getEquipment().getId(),
                    securityUtils.getCurrentUserId(),
                    HistoryAction.CONTRACT_CANCELLED,
                    String.format("Contrato com o equipamento cancelado com motivo: %s", request.reason())
            );
        });

        repository.save(contract);
    }

    private ContractEntity createContractEntity(
            ContractRequest request,
            UserEntity customer, ContractCreationOrigin origin
    ) {
        ContractEntity contract = ContractEntity.builder()
                .customer(customer)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .status(origin.getContractStatus())
                .totalValue(BigDecimal.ZERO)
                .notes(request.notes())
                .build();

        for (RentalItemRequest item : request.rentalItems()) {
            EquipmentEntity equipment = origin.applyEquipmentAction(equipmentService, item.equipmentId());
            BigDecimal itemValue = calculateRentalCost(equipment.getDailyRentalCost(), request.startDate(), request.endDate());

            var employeeOriginated = origin.equals(ContractCreationOrigin.EMPLOYEE);
            HistoryAction action = employeeOriginated ? HistoryAction.CONTRACT_CREATED : HistoryAction.CONTRACT_REQUESTED;
            String actionInText = employeeOriginated ? "criado" : "requisitado";

            historyService.register(
                    equipment.getId(),
                    securityUtils.getCurrentUserId(),
                    action,
                    String.format("Contrato com o equipamento foi %s", actionInText)
            );

            RentalEntity rental = RentalEntity.builder()
                    .equipment(equipment)
                    .startDate(contract.getStartDate())
                    .expectedEndDate(contract.getEndDate())
                    .totalValue(itemValue)
                    .status(origin.getRentalStatus())
                    .notes(item.notes())
                    .requiresMaintenance(false)
                    .build();

            contract.addRental(rental);
            contract.setTotalValue(contract.getTotalValue().add(rental.getTotalValue()));
        }

        return contract;
    }

    private BigDecimal calculateRentalCost(BigDecimal baseCost, LocalDate startDate, LocalDate expectedEndDate) {
        long duration = startDate.datesUntil(expectedEndDate).count();

        return baseCost.multiply(BigDecimal.valueOf(duration));
    }
}
