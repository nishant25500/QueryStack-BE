package com.mishri.question_service.dto;

import jakarta.validation.constraints.*;

import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 100, message = "Content must be between 10 and 100 characters")
    private String content;

}
