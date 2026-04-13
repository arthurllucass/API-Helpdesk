package com.arthurllucass.projeto_helpdesk.controller.exceptions;

import com.arthurllucass.projeto_helpdesk.exceptions.InvalidDataException;
import com.arthurllucass.projeto_helpdesk.exceptions.ObjectNotFoundException;
import com.arthurllucass.projeto_helpdesk.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException erro, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Não encontrado",
                erro.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExists(UserAlreadyExistsException erro, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Conflito de dados",
                erro.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<StandardError> invalidData(InvalidDataException erro, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Campo inválido",
                erro.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException erro, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de validação",
                "Campos inválidos",
                request.getRequestURI()
        );

        for (FieldError fieldError : erro.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationError> constraintViolation(ConstraintViolationException erro, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(
                System.currentTimeMillis(),
                status.value(),
                "Erro de validação",
                "Campos inválidos",
                request.getRequestURI()
        );

        erro.getConstraintViolations().forEach(v -> {
            String field = v.getPropertyPath().toString();
            String message = v.getMessage();
            error.addError(field, message);
        });
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity<StandardError> handleDateTimeParse(
            java.time.format.DateTimeParseException erro,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                status.value(),
                "Formato de data inválido",
                "Use o formato: yyyy-MM-ddTHH:mm:ss",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }
}
