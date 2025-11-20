package com.martingarrote.equip_rental.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final ErrorMessage errorMessage;
    private final HttpStatus status;

    public ServiceException(ErrorMessage errorMessage) {
        super(errorMessage.getDefaultMessage());
        this.errorMessage = errorMessage;
        this.status = errorMessage.getStatus();
    }

    public ServiceException(ErrorMessage errorMessage, String customMessage) {
        super(customMessage);
        this.errorMessage = errorMessage;
        this.status = errorMessage.getStatus();
    }
}