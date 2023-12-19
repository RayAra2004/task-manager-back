package com.example.desafiobrickUpback.controllers;

import com.example.desafiobrickUpback.dtos.TaskRecordDto;
import com.example.desafiobrickUpback.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTask(@RequestParam("description") @NotBlank String description,
                                               @RequestParam("status") @NotBlank String status,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile){
        try {
            TaskRecordDto data = objectMapper.readValue(
                    objectMapper.writeValueAsString(Map.of(
                    "description", description,
                    "status", status
                    )), TaskRecordDto.class
            );
            taskService.registerTask(data, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
