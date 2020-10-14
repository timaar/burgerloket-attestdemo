package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AttestNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(AttestNotFoundException ex) {

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .title("An error occurred!")
                .detail(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .title("An error occurred!")
                .detail(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .title("An error occurred!")
                .detail(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
