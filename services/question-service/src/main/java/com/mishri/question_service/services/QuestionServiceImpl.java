package com.mishri.question_service.services;

import com.mishri.question_service.dto.CursorPageResponse;
import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.events.ViewCountEvent;
import com.mishri.question_service.exception.QuestionAccessDeniedException;
import com.mishri.question_service.exception.QuestionNotFoundException;
import com.mishri.question_service.mappers.QuestionMapper;
import com.mishri.question_service.models.Question;
import com.mishri.question_service.producers.KafkaEventProducer;
import com.mishri.question_service.repositories.QuestionRepository;
import com.mishri.question_service.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor //this is doing constructor injection
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final KafkaEventProducer kafkaEventProducer;


    @Override
    public Mono<QuestionResponseDTO> createQuestion(
            QuestionRequestDTO request,
            String email
    ) {
        Question question = questionMapper.toModel(request);
        question.setCreatedBy(email);
        question.setViews(0);

        log.info("Creating question for user {}", email);

        return questionRepository.save(question)
                .map(questionMapper::toDto)
                .doOnSuccess(q ->
                        log.info("Question {} created successfully", q.getId()))
                .doOnError(e ->
                        log.error("Failed to create question", e));
    }



    @Override
    public Flux<QuestionResponseDTO> searchQuestion(String searchTerm, int pageNumber, int pageSize){
        //limit and offset based pagination

        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by("createdAt").descending());

        Flux<QuestionResponseDTO> response = questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm,pageable)
                .map(questionMapper::toDto)
                .doOnError(error -> log.error("Error searching question",error))
                .doOnComplete(() -> log.info("Successfully question searched"));

        return response;
    }

    @Override
    public Mono<CursorPageResponse<QuestionResponseDTO>> getAllQuestions(String cursor, int pageSize){
        //cursor based pagination
        Flux<Question> questionFlux;

        Pageable pageable = PageRequest.of(0,pageSize+1, Sort.by("createdAt").descending());  //we only want initial set of records from where cond.

        log.info(
                "Fetching questions. Cursor={}, PageSize={}",
                cursor,
                pageSize
        );

        if(CursorUtils.isValidCursor(cursor)){
            Instant cursorTimestamp = CursorUtils.parseCursor(cursor);
            questionFlux = questionRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(cursorTimestamp,pageable);
        }else{
            questionFlux = questionRepository.findAllByOrderByCreatedAtDesc(pageable);
        }

        return questionFlux.map(questionMapper::toDto)
                .collectList()
                .doOnSuccess(page -> log.info("Questions fetched successfully"))
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

        return questionRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new QuestionNotFoundException("Question not found"))
                )
                .map(questionMapper::toDto)
                .doOnSuccess(question -> {
                    log.info("Question fetched successfully: {}", question.getId());

                    kafkaEventProducer.publishViewCountEvent(
                            new ViewCountEvent(id, "question", Instant.now())
                    );
                })
                .doOnError(error ->
                        log.error("Failed to fetch question {}", id, error)
                );
    }

    @Override
    public Mono<QuestionResponseDTO> updateQuestion(
            String id,
            QuestionRequestDTO request,
            String email
    ) {

        return questionRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new QuestionNotFoundException("Question not found"))
                )
                .flatMap(question -> {

                    if (!question.getCreatedBy().equals(email)) {
                        return Mono.error(
                                new QuestionAccessDeniedException(
                                        "You are not allowed to edit this question."
                                )
                        );
                    }

                    question.setTitle(request.getTitle());
                    question.setContent(request.getContent());

                    return questionRepository.save(question);
                })
                .map(questionMapper::toDto)
                .doOnSuccess(q ->
                        log.info("Question {} updated successfully", q.getId()))
                .doOnError(e ->
                        log.error("Failed to update question {}", id, e));
    }

    @Override
    public Mono<Void> deleteQuestion(String id, String email) {

        return questionRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new QuestionNotFoundException("Question not found"))
                )
                .flatMap(question -> {

                    if (!question.getCreatedBy().equals(email)) {
                        return Mono.error(
                                new QuestionAccessDeniedException(
                                        "You are not allowed to delete this question."
                                )
                        );
                    }

                    return questionRepository.deleteById(id);
                })
                .doOnSuccess(unused ->
                        log.info("Question {} deleted successfully", id))
                .doOnError(error ->
                        log.error("Failed to delete question {}", id, error));
    }
}
