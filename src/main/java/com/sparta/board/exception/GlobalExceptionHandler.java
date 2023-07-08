package com.sparta.board.exception;

import com.sparta.board.dto.StatusCodesResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// RestController내에서 발생하는 예외들을 처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusCodesResponseDto> handleException(IllegalArgumentException ex) {
        StatusCodesResponseDto restApiException = new StatusCodesResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
    // MethodArgumentNotValidException (requestDto에서 valid 관련해서 생기는 예외) 처리
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StatusCodesResponseDto> handleException(MethodArgumentNotValidException ex) {
        StatusCodesResponseDto restApiException = new StatusCodesResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
