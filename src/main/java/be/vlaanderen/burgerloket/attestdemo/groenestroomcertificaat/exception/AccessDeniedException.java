package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception;

import lombok.Data;

@Data
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Exception throwable) {
        super(message, throwable);
    }
}
