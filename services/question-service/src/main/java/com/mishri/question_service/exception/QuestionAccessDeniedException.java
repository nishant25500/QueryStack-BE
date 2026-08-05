package com.mishri.question_service.exception;

public class QuestionAccessDeniedException extends RuntimeException{
    public QuestionAccessDeniedException(String message) {
        super(message);
    }
}
