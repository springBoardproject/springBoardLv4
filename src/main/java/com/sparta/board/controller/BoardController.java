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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
//        return boardService.createBoard(requestDto, user);
        return new ResponseEntity<>(boardService.createBoard(requestDto, user), HttpStatus.CREATED) ;
    }

    @GetMapping()
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
//        return boardService.getBoards();
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getSelectedBoard(@PathVariable Long id) {
//        return boardService.getSelectedBoard(id);
        return new ResponseEntity<>(boardService.getSelectedBoard(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
                                        @RequestBody @Valid BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
//        return boardService.updateBoard(id, requestDto, user);
        return new ResponseEntity<>(boardService.updateBoard(id, requestDto, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusCodesResponseDto> deleteBoard(@PathVariable Long id,
                                      HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
//        String token = authentication(req);
        return new ResponseEntity<>(boardService.deleteBoard(id, user), HttpStatus.OK);
//        return ResponseEntity.ok(boardService.deleteBoard(id, user));
    }

//    private String authentication(HttpServletRequest req) {
//        // 토큰값 가져오기
//        String tokenValue = jwtUtil.getTokenFromRequest(req);
//        // JWT 토큰 substring
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증
//        if(!jwtUtil.validateToken(token)){
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        return token;
//    }
}
