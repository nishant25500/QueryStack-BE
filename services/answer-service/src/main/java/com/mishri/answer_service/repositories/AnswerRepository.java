package com.mishri.answer_service.repositories;

import com.mishri.answer_service.models.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {

    Flux<Answer> findByQuestionIdOrderByCreatedAtAsc(String questionId);
}