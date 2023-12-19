package com.example.desafiobrickUpback.services;

import com.example.desafiobrickUpback.dtos.TaskRecordDto;
import com.example.desafiobrickUpback.models.Status;
import com.example.desafiobrickUpback.models.TaskModel;
import com.example.desafiobrickUpback.repositories.TaskRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public void registerTask(TaskRecordDto data, MultipartFile imageFile){
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

    }

    public List<TaskModel> getTasks(){
        return taskRepository.findAll();
    }

    public void updateTask(TaskRecordDto data, MultipartFile imageFile){
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
    }

    public void finishTask(String id){
        Optional<TaskModel> optionalTaskModel = taskRepository.findById(UUID.fromString(id));

        if(optionalTaskModel.isPresent()){
            TaskModel taskModel = optionalTaskModel.get();
            taskModel.setStatus(Status.FINISHED);
        }else{
            throw new EntityNotFoundException();
        }
    }
}
