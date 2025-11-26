package com.martingarrote.equip_rental.domain.contract;

import com.martingarrote.equip_rental.domain.contract.dto.ContractActionRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractRequest;
import com.martingarrote.equip_rental.domain.contract.dto.ContractResponse;
import com.martingarrote.equip_rental.infrastructure.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ContractService {

    ContractResponse createByCustomer(ContractRequest request, String customerEmail);
    ContractResponse createByEmployee(ContractRequest request);
    ContractResponse retrieve(UUID id);
    ContractResponse retrieveByCustomer(UUID id, String customerEmail);
    PageResponse<ContractResponse> list(ContractStatus status, UUID customerId, Pageable pageable);
    PageResponse<ContractResponse> listByCustomer(String customerEmail, ContractStatus status, Pageable pageable);
    PageResponse<ContractResponse> findExpiring(Integer daysAhead, Pageable pageable);
    PageResponse<ContractResponse> findPendingApproval(Pageable pageable);
    void approve(UUID id);
    void reject(UUID id, ContractActionRequest request);
    void cancel(UUID id, String requesterEmail, ContractActionRequest request);
}
