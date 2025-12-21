package com.mishri.question_service.repositories;

import com.mishri.question_service.models.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {
}
