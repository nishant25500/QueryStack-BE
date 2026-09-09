package com.mishri.answer_service.clients;

import com.mishri.answer_service.dto.response.ApiResponse;
import com.mishri.answer_service.dto.response.QuestionResponseDTO;
import com.mishri.answer_service.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.core.ParameterizedTypeReference;

@Component
@RequiredArgsConstructor
public class QuestionServiceClient {

    private final WebClient questionWebClient;

    public Mono<QuestionResponseDTO> getQuestionById(String questionId) {

        return questionWebClient
                .get()
                .uri("/api/question/{id}", questionId)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(
                                new QuestionNotFoundException("Question not found!")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<QuestionResponseDTO>>() {})
                .map(ApiResponse::getData);
    }
}