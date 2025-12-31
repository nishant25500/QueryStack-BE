package com.mishri.question_service.dto;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {
    private String id;
    private String title;
    private String content;
    private Instant createdAt;

//    public Instant getCreatedAt() {
//        return createdAt;
//    }
}
