package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.StatusCodesResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    public CommentResponseDto createComment(Long boardId, CommentRequestDto requestDto, User user) {
        // 해당 게시글이 DB에 존재하는지 확인
        Board targetBoard = boardService.findBoard(boardId);
        // requestDto를 포함한 comment 저장에 필요한 값들 담아서 주기
        Comment comment = new Comment(requestDto, targetBoard, user);
        // DB 저장 넘겨주기
        Comment saveComment = commentRepository.save(comment);
        // Entity -> ResponseDto
        return new CommentResponseDto(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        // 댓글 저장유무 확인
        Comment comment = findComment(commentId);
        // 권한 확인
        jwtUtil.checkAuthority(comment, user);
        // 수정
        comment.update(requestDto);
        // Entity -> ResponseDto
        return new CommentResponseDto(comment);
    }

    public StatusCodesResponseDto deleteComment(Long commentId, User user) {
        // 댓글 저장유무 확인
        Comment comment = findComment(commentId);
        // 권한 확인
        jwtUtil.checkAuthority(comment, user);
        // 삭제
        commentRepository.delete(comment);

        return new StatusCodesResponseDto(HttpStatus.OK.value(), "삭제가 완료 되었습니다.");

    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시물 입니다.")
        );
    }
}
