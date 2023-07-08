package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
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

//    private String username;

    @ManyToOne
//    @JoinColumn(name="comment_id")
    private Board board;

    @ManyToOne
    private User user;

    public Comment(CommentRequestDto requestDto, Board board, User user) {
        this.comments = requestDto.getComments();
        this.board = board;  //지금 comment가 board랑 연관관계없이 저장하고있길래수정중입니다.
        this.user = user;
//        this.username = user.getUsername();
    }

    public void update(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }

}
