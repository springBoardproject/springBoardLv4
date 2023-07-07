package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "boards")
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "username", nullable = false) // foreign key 어캐하지?
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    public Board(BoardRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
