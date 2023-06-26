package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) { // orm과 유사한 기능을 위해 필요
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        // DB 저장 넘겨주기
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);

        return boardResponseDto;
    }
    public List<BoardResponseDto> getBoards() {
        // db 조회 넘겨주기
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto getSelectedBoard(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);
        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);
        // 비밀번호 확인
        checkPassword(board, requestDto);
        // 수정
        board.update(requestDto);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    public Long deleteBoard(Long id, BoardRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);
        // 비밀번호 확인
        checkPassword(board, requestDto);
        // 삭제
        boardRepository.delete(board);
        return id;

    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 게시물 입니다.")
        );
    }

    private void checkPassword(Board board, BoardRequestDto requestDto) {
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }
}
