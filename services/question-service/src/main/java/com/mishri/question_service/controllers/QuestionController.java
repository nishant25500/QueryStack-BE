package com.mishri.question_service.controllers;

import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.services.IQuestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final IQuestionService questionService;

    QuestionController(IQuestionService _questionService){
        this.questionService = _questionService;
    }

    @PostMapping("/create")
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO req){
        return questionService.createQuestion(req)
                .doOnSuccess(res -> System.out.println("Question created successfully" + res ))
                .doOnError(error -> System.out.println("Error creating question" + error));
    }
}
