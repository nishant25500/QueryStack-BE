package com.mishri.question_service.services;

import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.models.Question;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO request);
}
