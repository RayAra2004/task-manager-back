package com.example.desafiobrickUpback.controllers;

import com.example.desafiobrickUpback.dtos.TaskRecordDto;
import com.example.desafiobrickUpback.models.Status;
import com.example.desafiobrickUpback.models.TaskModel;
import com.example.desafiobrickUpback.repositories.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
@Validated
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

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

            Status statusTask = (data.status().equalsIgnoreCase("pending")) ? Status.PENDING : Status.FINISHED;

            byte[] imageData = null;
            try{
                if(imageFile != null && !imageFile.isEmpty()) {
                    imageData = imageFile.getBytes();
                }
            }catch (IOException e){
                throw new RuntimeException("Erro ao processar a imagem", e);
            }

            if (imageData == null) {
                var newTaskModel = new TaskModel(data.description(), statusTask);
                taskRepository.save(newTaskModel);
            }else{
                var newTaskModel = new TaskModel(data.description(), statusTask, imageData);
                taskRepository.save(newTaskModel);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> getTasks(){
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTask(@PathVariable String id,
                                     @RequestParam("description") @NotBlank String description,
                                     @RequestParam("status") @NotBlank String status,
                                     @RequestParam(value = "image", required = false) MultipartFile imageFile){
        try{
            TaskRecordDto data = objectMapper.readValue(
                    objectMapper.writeValueAsString(Map.of(
                            "description", description,
                            "status", status,
                            "id", id
                    )), TaskRecordDto.class
            );
            Optional<TaskModel> optionalTaskModel = taskRepository.findById(UUID.fromString(data.id()));

            if(optionalTaskModel.isPresent()){
                Status statusTask = (data.status().equalsIgnoreCase("pending")) ? Status.PENDING : Status.FINISHED;

                byte[] imageData = null;
                try{
                    if(imageFile != null && !imageFile.isEmpty()) {
                        imageData = imageFile.getBytes();
                    }
                }catch (IOException e){
                    throw new RuntimeException("Erro ao processar a imagem", e);
                }

                TaskModel taskModel = optionalTaskModel.get();
                taskModel.setDescription(data.description());
                taskModel.setImage(imageData);
                taskModel.setStatus(statusTask);

            }else{
                throw new EntityNotFoundException();
            }

            return ResponseEntity.status(HttpStatus.OK).body("Tarefa atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/finish/{id}")
    @Transactional
    public ResponseEntity finishTask(@PathVariable String id){
        try{
            Optional<TaskModel> optionalTaskModel = taskRepository.findById(UUID.fromString(id));

            if(optionalTaskModel.isPresent()){
                TaskModel taskModel = optionalTaskModel.get();
                taskModel.setStatus(Status.FINISHED);
            }else{
                throw new EntityNotFoundException();
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
