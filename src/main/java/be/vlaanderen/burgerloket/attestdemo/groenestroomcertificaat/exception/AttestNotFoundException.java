package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception;

import lombok.Data;

@Data
public class AttestNotFoundException extends RuntimeException {

    public AttestNotFoundException(String message) {
        super(message);
    }

    public AttestNotFoundException(String message, Exception throwable) {
        super(message, throwable);
    }
}
