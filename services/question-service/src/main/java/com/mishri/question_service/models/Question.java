package com.mishri.question_service.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "questions")
public class Question extends BaseModel {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 100, message = "Content must be between 10 and 100 characters")
    private String content;


}
