package com.mishri.answer_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AnswerResponse {

    private String id;

    private String questionId;

    private String content;

    private String createdBy;

    private Instant createdAt;

    private Instant updatedAt;
}