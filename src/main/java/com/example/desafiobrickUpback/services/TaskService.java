package com.example.desafiobrickUpback.services;

import com.example.desafiobrickUpback.dtos.TaskRecordDto;
import com.example.desafiobrickUpback.models.Status;
import com.example.desafiobrickUpback.models.TaskModel;
import com.example.desafiobrickUpback.repositories.TaskRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
