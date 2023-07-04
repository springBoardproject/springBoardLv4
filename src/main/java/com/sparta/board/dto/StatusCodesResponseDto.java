package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class StatusCodesResponseDto {
    private String message;
    private int statusCode;

    public StatusCodesResponseDto(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
