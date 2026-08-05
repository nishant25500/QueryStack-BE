package com.mishri.question_service.exception;

import com.mishri.question_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleQuestionNotFound(
            QuestionNotFoundException ex
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.<Void>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InvalidCursorException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCursor(
            InvalidCursorException ex
    ) {

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<Void>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(QuestionAccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleQuestionAccessDenied(QuestionAccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ApiResponse.<Void>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex
    ) {

        return ResponseEntity.internalServerError()
                .body(
                        ApiResponse.<Void>builder()
                                .success(false)
                                .message("Something went wrong")
                                .build()
                );
    }
}