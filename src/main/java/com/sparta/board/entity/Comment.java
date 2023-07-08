package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="comments")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comments;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    public Comment(CommentRequestDto requestDto, Board board, User user) {
        this.comments = requestDto.getComments();
        this.board = board;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }

}
