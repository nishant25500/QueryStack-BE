package com.mishri.answer_service.dto.request;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class CreateAnswerRequest {

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 5000, message = "Content must be between 10 and 5000 characters")
    private String content;

    @NotBlank(message = "Question id is required")
    private String questionId;
}