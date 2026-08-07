package com.mishri.answer_service.controllers;

import com.mishri.answer_service.dto.request.CreateAnswerRequest;
import com.mishri.answer_service.dto.request.UpdateAnswerRequest;
import com.mishri.answer_service.dto.response.AnswerResponse;
import com.mishri.answer_service.dto.response.ApiResponse;
import com.mishri.answer_service.services.IAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerController {

    private final IAnswerService answerService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<AnswerResponse>>> createAnswer(
            @Valid @RequestBody CreateAnswerRequest request,
            Authentication authentication
    ) {

        return answerService.createAnswer(request, authentication.getName())
                .map(response ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(
                                        ApiResponse.<AnswerResponse>builder()
                                                .success(true)
                                                .message("Answer created successfully")
                                                .data(response)
                                                .build()
                                )
                )
                .doOnSuccess(res ->
                        log.info("Answer created by {}", authentication.getName()))
                .doOnError(error ->
                        log.error("Error creating answer", error));
    }

    @GetMapping("/question/{questionId}")
    public Mono<ResponseEntity<ApiResponse<List<AnswerResponse>>>> getAnswersByQuestionId(
            @PathVariable String questionId
    ) {

        return answerService.getAnswersByQuestionId(questionId)
                .collectList()
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<List<AnswerResponse>>builder()
                                        .success(true)
                                        .message("Answers fetched successfully")
                                        .data(response)
                                        .build()
                        )
                )
                .doOnSuccess(res ->
                        log.info("Fetched answers for question {}", questionId))
                .doOnError(error ->
                        log.error("Error fetching answers", error));
    }

    @PutMapping("/{answerId}")
    public Mono<ResponseEntity<ApiResponse<AnswerResponse>>> updateAnswer(
            @PathVariable String answerId,
            @Valid @RequestBody UpdateAnswerRequest request,
            Authentication authentication
    ) {

        return answerService.updateAnswer(
                        answerId,
                        request,
                        authentication.getName()
                )
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<AnswerResponse>builder()
                                        .success(true)
                                        .message("Answer updated successfully")
                                        .data(response)
                                        .build()
                        )
                )
                .doOnSuccess(res ->
                        log.info("Answer {} updated by {}", answerId, authentication.getName()))
                .doOnError(error ->
                        log.error("Error updating answer {}", answerId, error));
    }

    @DeleteMapping("/{answerId}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteAnswer(
            @PathVariable String answerId,
            Authentication authentication
    ) {

        return answerService.deleteAnswer(
                        answerId,
                        authentication.getName()
                )
                .thenReturn(
                        ResponseEntity.ok(
                                ApiResponse.<Void>builder()
                                        .success(true)
                                        .message("Answer deleted successfully")
                                        .build()
                        )
                )
                .doOnSuccess(res ->
                        log.info("Answer {} deleted", answerId, authentication.getName()))
                .doOnError(error ->
                        log.error("Error deleting answer {}", answerId, error));
    }
}