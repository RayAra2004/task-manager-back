package com.example.desafiobrickUpback.exceptions;

import com.example.desafiobrickUpback.dtos.ExceptionDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> threat400(){
        ExceptionDto response = new ExceptionDto("Faltam parâmetros", 400);
        return ResponseEntity.badRequest().body(response);
    }
}
