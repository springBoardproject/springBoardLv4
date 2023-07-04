package com.sparta.board.exception;

import com.sparta.board.dto.StatusCodesResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusCodesResponseDto> handleException(IllegalArgumentException ex) {
        StatusCodesResponseDto restApiException = new StatusCodesResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StatusCodesResponseDto> handleException(MethodArgumentNotValidException ex) {
        StatusCodesResponseDto restApiException = new StatusCodesResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
