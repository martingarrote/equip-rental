package com.martingarrote.equip_rental.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;
    private final HttpStatus status;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getDefaultMessage());
        this.errorMessage = errorMessage;
        this.status = errorMessage.getStatus();
    }

    public BusinessException(ErrorMessage errorMessage, String customMessage) {
        super(customMessage);
        this.errorMessage = errorMessage;
        this.status = errorMessage.getStatus();
    }
}