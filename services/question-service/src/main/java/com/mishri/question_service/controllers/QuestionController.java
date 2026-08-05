package com.mishri.question_service.controllers;

import com.mishri.question_service.dto.CursorPageResponse;
import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.dto.response.ApiResponse;
import com.mishri.question_service.services.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final IQuestionService questionService;

//    QuestionController(IQuestionService _questionService){
//        this.questionService = _questionService;
//    }

    @PostMapping("/create")
    public Mono<ResponseEntity<ApiResponse<QuestionResponseDTO>>> createQuestion(
            @Valid @RequestBody QuestionRequestDTO req,
            Authentication authentication
    ) {

        return questionService.createQuestion(req, authentication.getName())
                .map(response ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(
                                        ApiResponse.<QuestionResponseDTO>builder()
                                                .success(true)
                                                .message("Question created successfully")
                                                .data(response)
                                                .build()
                                )
                )
                .doOnSuccess(res -> log.info(
                        "Question created successfully by {}",
                        authentication.getName()
                ))
                .doOnError(error -> log.error("Error creating question", error));
    }


//    @PostMapping("/create")
//    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO req){
//        return questionService.createQuestion(req)
//                .doOnSuccess(res -> System.out.println("Question created successfully" + res ))
//                .doOnError(error -> System.out.println("Error creating question" + error));
//    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<QuestionResponseDTO>>> getQuestionById(
            @PathVariable String id
    ) {

        return questionService.getQuestionById(id)
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<QuestionResponseDTO>builder()
                                        .success(true)
                                        .message("Question fetched successfully")
                                        .data(response)
                                        .build()
                        )
                )
                .doOnSuccess(res -> log.info("Question fetched successfully"))
                .doOnError(error -> log.error("Error fetching question {}", id, error));
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<ApiResponse<CursorPageResponse<QuestionResponseDTO>>>> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        return questionService.getAllQuestions(cursor, pageSize)
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<CursorPageResponse<QuestionResponseDTO>>builder()
                                        .success(true)
                                        .message("Questions fetched successfully")
                                        .data(response)
                                        .build()
                        )
                )
                .doOnSuccess(res -> log.info("Questions fetched successfully"))
                .doOnError(error -> log.error("Error fetching questions", error));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id){
        throw new UnsupportedOperationException("Not supported op");
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<ApiResponse<List<QuestionResponseDTO>>>> search(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        return questionService.searchQuestion(searchTerm, pageNumber, pageSize)
                .collectList()
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<List<QuestionResponseDTO>>builder()
                                        .success(true)
                                        .message("Questions fetched successfully")
                                        .data(response)
                                        .build()
                        )
                )
                .doOnSuccess(res -> log.info("Search completed for '{}'", searchTerm))
                .doOnError(error ->
                        log.error("Error searching questions for '{}'", searchTerm, error));
    }

    @GetMapping("tag/{tag}")
    public Mono<ResponseEntity<ApiResponse<List<QuestionResponseDTO>>>> getQuestionByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        throw new UnsupportedOperationException("Not supported op");
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<QuestionResponseDTO>>> updateQuestion(
            @PathVariable String id,
            @Valid @RequestBody QuestionRequestDTO request,
            Authentication authentication
    ) {

        return questionService.updateQuestion(
                        id,
                        request,
                        authentication.getName()
                )
                .map(response ->
                        ResponseEntity.ok(
                                ApiResponse.<QuestionResponseDTO>builder()
                                        .success(true)
                                        .message("Question updated successfully")
                                        .data(response)
                                        .build()
                        )
                );
    }
}
