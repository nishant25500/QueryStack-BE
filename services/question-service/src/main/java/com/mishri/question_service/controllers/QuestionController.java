package com.mishri.question_service.controllers;

import com.mishri.question_service.dto.CursorPageResponse;
import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.services.IQuestionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
@RequestMapping("/api/question")
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


    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id){
        return questionService.getQuestionById(id)
                .doOnSuccess(r -> System.out.println("Questions fetched successfully"))
                .doOnError(error -> System.out.println("Error fetching questions" + error));
    }

    @GetMapping("/all")
    public Mono<CursorPageResponse<QuestionResponseDTO>> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return questionService.getAllQuestions(cursor,pageSize)
                .doOnSuccess(r -> System.out.println("Questions fetched successfully"))
                .doOnError(error -> System.out.println("Error fetching questions" + error));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id){
        throw new UnsupportedOperationException("Not supported op");
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> search(
      @RequestParam String searchTerm,
      @RequestParam(defaultValue = "0") int pageNumber,  //here cursor will be timeStamp [string]
      @RequestParam(defaultValue = "10") int pageSize
    ){
        return questionService.searchQuestion(searchTerm,pageNumber,pageSize);
    }

    @GetMapping("tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        throw new UnsupportedOperationException("Not supported op");
    }
}
