package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.infrastructure.exception.ErrorMessage;
import com.martingarrote.equip_rental.infrastructure.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDataProvider {

    private final UserRepository repository;

    public UserEntity getEntityById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    public UserEntity getEntityByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new ServiceException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    public UUID getUserIdByEmail(String email) {
        return repository.getIdByEmail(email).orElseThrow(
                () -> new ServiceException(ErrorMessage.USER_NOT_FOUND)
        );
    }
}
