package com.estepnv.hotel_advisor.web_utils;

import com.estepnv.hotel_advisor.exceptions.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .toList();

        ErrorResponse response = new ErrorResponse(
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> hangleGenericError(ApplicationException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ErrorResponse DTO
    public record ErrorResponse(String message, List<String> errors) {}
}