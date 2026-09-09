package com.mishri.answer_service.services;

import com.mishri.answer_service.dto.request.CreateAnswerRequest;
import com.mishri.answer_service.dto.request.UpdateAnswerRequest;
import com.mishri.answer_service.dto.response.AnswerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    Mono<AnswerResponse> createAnswer(
            CreateAnswerRequest request,
            String email
    );

    Flux<AnswerResponse> getAnswersByQuestionId(
            String questionId
    );

    Mono<AnswerResponse> updateAnswer(
            String answerId,
            UpdateAnswerRequest request,
            String email
    );

    Mono<Void> deleteAnswer(
            String answerId,
            String email
    );
}