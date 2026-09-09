package com.mishri.answer_service.dto.response;

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
    private Integer views;
    private String createdBy;
    private Instant createdAt;


//    public Instant getCreatedAt() {
//        return createdAt;
//    }
}
