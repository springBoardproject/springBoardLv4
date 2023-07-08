package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.StatusCodesResponseDto;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    //게시글 작성
    @PostMapping()
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        User user = jwtUtil.getUserInfoFromRequest(request);
        return new ResponseEntity<>(boardService.createBoard(requestDto, user), HttpStatus.CREATED) ;
    }
    // 작성시간 기준으로 내림차순 정렬하여 모든 게시글 출력
    @GetMapping()
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        return new ResponseEntity<>(boardService.getAllBoards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getSelectedBoard(@PathVariable Long id) {
        return new ResponseEntity<>(boardService.getSelectedBoard(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
                                        @RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        User user = jwtUtil.getUserInfoFromRequest(request);
        return new ResponseEntity<>(boardService.updateBoard(id, requestDto, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusCodesResponseDto> deleteBoard(@PathVariable Long id,
                                      HttpServletRequest request) {
        User user = jwtUtil.getUserInfoFromRequest(request);
        return new ResponseEntity<>(boardService.deleteBoard(id, user), HttpStatus.OK);
    }
}
