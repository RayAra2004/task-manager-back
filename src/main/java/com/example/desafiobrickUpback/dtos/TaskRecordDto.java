package com.example.desafiobrickUpback.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDto(@NotBlank String description, @NotBlank String status, String image, String id) {
}
