package com.sparta.board.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.dto.StatusCodesResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
@Order(1)
public class AuthExceptionFilter extends OncePerRequestFilter {

    // 제대로 이해 못한 상태?
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            StatusCodesResponseDto statusCodesResponseDto = new StatusCodesResponseDto(HttpStatus.BAD_REQUEST.value(), "토큰이 유효하지 않습니다.");

            String json = new ObjectMapper().writeValueAsString(statusCodesResponseDto);

            response.getWriter().write(json);
        }
    }
}