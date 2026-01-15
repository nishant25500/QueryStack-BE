package com.mishri.question_service.services;

import com.mishri.question_service.dto.CursorPageResponse;
import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.events.ViewCountEvent;
import com.mishri.question_service.mappers.QuestionMapper;
import com.mishri.question_service.models.Question;
import com.mishri.question_service.producers.KafkaEventProducer;
import com.mishri.question_service.repositories.QuestionRepository;
import com.mishri.question_service.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor //this is doing constructor injection
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final KafkaEventProducer kafkaEventProducer;


    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO request) {
        Question question = questionMapper.toModel(request);
        Mono<Question> questionMono = questionRepository.save(question);  //this publisher
        Mono<QuestionResponseDTO> response = questionMono.map(questionMapper::toDto)  //subscriber
                .doOnSuccess(res -> System.out.println("Question created successfully" + res ))
                .doOnError(error -> System.out.println("Error creating question" + error));
        return response;
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestion(String searchTerm, int pageNumber, int pageSize){
        //limit and offset based pagination

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Flux<QuestionResponseDTO> response = questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm,pageable)
                .map(questionMapper::toDto)
                .doOnError(error -> System.out.println("Error searching question"+error))
                .doOnComplete(() -> System.out.println("Successfully question searched"));

        return response;
    }

    @Override
    public Mono<CursorPageResponse<QuestionResponseDTO>> getAllQuestions(String cursor, int pageSize){
        //cursor based pagination
        Flux<Question> questionFlux;

        Pageable pageable = PageRequest.of(0,pageSize+1, Sort.by("createdAt").descending());  //we only want initial set of records from where cond.

        if(CursorUtils.isValidCursor(cursor)){
            Instant cursorTimeStamp = CursorUtils.parseCursor(cursor);
            questionFlux = questionRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(cursorTimeStamp,pageable);
        }else{
            questionFlux = questionRepository.findAllByOrderByCreatedAtDesc(pageable);
        }

        return questionFlux.map(questionMapper::toDto)
                .collectList()
                .map(list -> {
                    boolean hasNext = list.size() > pageSize;

                    if(hasNext){
                        list = list.subList(0,pageSize);
                    }

                    String nextCursor = hasNext ? list.get(list.size()-1).getCreatedAt().toString() : null;
                    return new CursorPageResponse<>(list, nextCursor, hasNext);
                });
    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id){

        Mono<Question> questionMono = questionRepository.findById(id);

        return questionMono.map(questionMapper::toDto)
                .doOnSuccess(res ->{
                    System.out.println("Question fetched successfully: "+res);
                    ViewCountEvent viewCountEvent = new ViewCountEvent(id,"question",Instant.now());
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                })
                .doOnError(err -> System.out.println("Error fetching question: " + err));
    }
}
