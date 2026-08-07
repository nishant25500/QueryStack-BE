package com.mishri.answer_service.services;

import com.mishri.answer_service.dto.request.CreateAnswerRequest;
import com.mishri.answer_service.dto.request.UpdateAnswerRequest;
import com.mishri.answer_service.dto.response.AnswerResponse;
import com.mishri.answer_service.exception.AnswerAccessDeniedException;
import com.mishri.answer_service.exception.AnswerNotFoundException;
import com.mishri.answer_service.mappers.AnswerMapper;
import com.mishri.answer_service.models.Answer;
import com.mishri.answer_service.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements IAnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Override
    public Mono<AnswerResponse> createAnswer(
            CreateAnswerRequest request,
            String email
    ) {

        Answer answer = answerMapper.toModel(request, email);

        return answerRepository.save(answer)
                .map(answerMapper::toResponse)
                .doOnSuccess(a ->
                        log.info("Answer {} created successfully", a.getId()))
                .doOnError(e ->
                        log.error("Failed to create answer", e));
    }

    @Override
    public Flux<AnswerResponse> getAnswersByQuestionId(
            String questionId
    ) {

        return answerRepository
                .findByQuestionIdOrderByCreatedAtAsc(questionId)
                .map(answerMapper::toResponse);
    }

    @Override
    public Mono<AnswerResponse> updateAnswer(
            String answerId,
            UpdateAnswerRequest request,
            String email
    ) {

        return answerRepository.findById(answerId)

                .switchIfEmpty(
                        Mono.error(
                                new AnswerNotFoundException("Answer not found")
                        )
                )

                .flatMap(answer -> {

                    if (!answer.getCreatedBy().equals(email)) {
                        return Mono.error(
                                new AnswerAccessDeniedException(
                                        "You can update only your own answers"
                                )
                        );
                    }

                    answer.setContent(request.getContent());

                    return answerRepository.save(answer);
                })

                .map(answerMapper::toResponse)

                .doOnSuccess(a ->
                        log.info("Answer {} updated successfully", a.getId()))

                .doOnError(e ->
                        log.error("Failed to update answer {}", answerId, e));
    }

    @Override
    public Mono<Void> deleteAnswer(
            String answerId,
            String email
    ) {

        return answerRepository.findById(answerId)

                .switchIfEmpty(
                        Mono.error(
                                new AnswerNotFoundException("Answer not found")
                        )
                )

                .flatMap(answer -> {

                    if (!answer.getCreatedBy().equals(email)) {
                        return Mono.error(
                                new AnswerAccessDeniedException(
                                        "You can delete only your own answers"
                                )
                        );
                    }

                    return answerRepository.delete(answer);
                })

                .doOnSuccess(v ->
                        log.info("Answer {} deleted successfully", answerId))

                .doOnError(e ->
                        log.error("Failed to delete answer {}", answerId, e));
    }
}