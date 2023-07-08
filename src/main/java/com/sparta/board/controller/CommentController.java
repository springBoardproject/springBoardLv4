package com.sparta.board.controller;

import com.sparta.board.dto.*;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.service.BoardService;
import com.sparta.board.service.CommentService;
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
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId,
                                                            @RequestBody @Valid CommentRequestDto requestDto,
                                                 HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(commentService.createComment(boardId, requestDto, user), HttpStatus.CREATED) ;
    }

    @PutMapping("/{commentid}")
    public ResponseEntity<CommentResponseDto> updateBoard(@PathVariable Long commentid,
                                                        @RequestBody @Valid CommentRequestDto requestDto,
                                                        HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
//        return boardService.updateBoard(id, requestDto, user);
        return new ResponseEntity<>(commentService.updateComment(commentid, requestDto, user), HttpStatus.OK);
    }

    @DeleteMapping("/{commentid}")
    public ResponseEntity<StatusCodesResponseDto> deleteBoard(@PathVariable Long commentid,
                                                              HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(commentService.deleteComment(commentid, user), HttpStatus.OK);
    }
}
