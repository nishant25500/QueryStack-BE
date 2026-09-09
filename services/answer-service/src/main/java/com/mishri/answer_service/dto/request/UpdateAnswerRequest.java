package com.mishri.answer_service.dto.request;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
public class UpdateAnswerRequest {

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
}