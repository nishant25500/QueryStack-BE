package com.mishri.answer_service.exception;

public class AnswerAccessDeniedException extends RuntimeException {

    public AnswerAccessDeniedException(String message) {
        super(message);
    }
}