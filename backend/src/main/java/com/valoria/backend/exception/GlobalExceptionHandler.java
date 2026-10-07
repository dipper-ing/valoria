package com.valoria.backend.exception;

import com.valoria.backend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CorreoYaRegistradoException.class)
    public ResponseEntity<ErrorResponse> manejarCorreoDuplicado(CorreoYaRegistradoException ex) {
        ErrorResponse cuerpo = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), Map.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(cuerpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse cuerpo = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Datos invalidos", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }
}