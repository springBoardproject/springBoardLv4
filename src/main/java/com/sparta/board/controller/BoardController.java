package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.StatusCodesResponseDto;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    public BoardResponseDto createBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest req) {

        String token = authentication(req);

        return boardService.createBoard(requestDto, token);
    }

    @GetMapping()
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")
    public BoardResponseDto getSelectedBoard(@PathVariable Long id) {
        return boardService.getSelectedBoard(id);
    }

    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest req) {
        String token = authentication(req);
        return boardService.updateBoard(id, requestDto, token);
    }

    @DeleteMapping("/{id}")
    public StatusCodesResponseDto deleteBoard(@PathVariable Long id,
                                              HttpServletRequest req) {
        String token = authentication(req);
        return boardService.deleteBoard(id, token);
    }

    private String authentication(HttpServletRequest req) {
        // 토큰값 가져오기
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        return token;
    }
}
