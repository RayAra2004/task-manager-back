package com.example.desafiobrickUpback.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ExceptionDto {
    String message;
    Integer status;
    public ExceptionDto(String message, Integer status){

        this.message = message;
        this.status = status;
    }
}
