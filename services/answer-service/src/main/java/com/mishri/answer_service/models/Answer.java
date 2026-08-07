package com.mishri.answer_service.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "answers")
public class Answer extends BaseModel {

    private String questionId;

    private String content;

    private String createdBy;
}
