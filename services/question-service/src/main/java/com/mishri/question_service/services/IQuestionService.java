package com.mishri.question_service.services;

import com.mishri.question_service.dto.CursorPageResponse;
import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.models.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO request);

    public Flux<QuestionResponseDTO> searchQuestion(String searchParam, int pageNumber, int pageSize);

    public Mono<CursorPageResponse<QuestionResponseDTO>> getAllQuestions(String cursor, int pageSize);

    public Mono<QuestionResponseDTO> getQuestionById(String id);
}
