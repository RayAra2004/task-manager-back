package com.example.desafiobrickUpback.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "TB_TASK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] image;
    @Enumerated(EnumType.STRING)
    private Status status;

    public TaskModel(String description, Status status){
        this.description = description;
        this.status = status;
    }

    public TaskModel(String description, Status statusTask, byte[] image) {
        this.description = description;
        this.status = statusTask;
        this.image = image;
    }
}





