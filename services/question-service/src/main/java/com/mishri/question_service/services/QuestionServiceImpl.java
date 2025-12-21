package com.mishri.question_service.services;

import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.mappers.QuestionMapper;
import com.mishri.question_service.models.Question;
import com.mishri.question_service.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
//@RequiredArgsConstructor //this is doing constructor injection
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    QuestionServiceImpl(QuestionRepository repo,QuestionMapper _questionMapper){
        this.questionMapper = _questionMapper;
        this.questionRepository = repo;
    }

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO request) {
        Question question = questionMapper.toModel(request);
        Mono<Question> questionMono = questionRepository.save(question);  //this publisher
        Mono<QuestionResponseDTO> response = questionMono.map(questionMapper::toDto)  //subscriber
                .doOnSuccess(res -> System.out.println("Question created successfully" + res ))
                .doOnError(error -> System.out.println("Error creating question" + error));
        return response;
    }
}
