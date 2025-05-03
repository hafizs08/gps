package com.example.gps.exception;

import com.example.gps.dto.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Hidden
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVehicleNotFound(VehicleNotFoundException ex, Locale locale) {
        String code = messageSource.getMessage("application.error.vehicle.not-found.code", null, locale);
        String message = messageSource.getMessage("application.error.vehicle.not-found.detail", ex.getArgs(), locale);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(code, message, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, Locale locale) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        messageSource.getMessage("application.error.invalid-arguments.code", null, locale),
                        messageSource.getMessage("application.error.invalid-arguments.detail", null, locale),
                        errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, Locale locale) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(path, message);
        });

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        messageSource.getMessage("application.error.invalid-arguments.code", null, locale),
                        messageSource.getMessage("application.error.invalid-arguments.detail", null, locale),
                        errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntry(DataIntegrityViolationException ex, Locale locale) {
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        logger.warn("Data integrity violation: {}", rootMessage);

        if (rootMessage != null && rootMessage.contains("plate_number")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorResponse(
                            messageSource.getMessage("application.error.duplicate.code", null, locale),
                            messageSource.getMessage("application.error.duplicate.plate_number", null, locale),
                            Map.of("plate_number",
                                    messageSource.getMessage("application.error.duplicate.plate_number.detail", null, locale))));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        messageSource.getMessage("application.error.duplicate.code", null, locale),
                        messageSource.getMessage("application.error.duplicate.generic", null, locale),
                        null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, Locale locale) {
        logger.error("Unhandled exception caught: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        messageSource.getMessage("application.error.internal.code", null, locale),
                        messageSource.getMessage("application.error.internal.detail", null, locale),
                        null));
    }
}
