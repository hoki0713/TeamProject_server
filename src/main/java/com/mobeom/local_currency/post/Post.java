package com.mobeom.local_currency.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.board.Board;
import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter @Setter @ToString
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id", nullable = false)
    private Long postId;

    @Column(name="reg_date")
    private LocalDate regDate = LocalDate.now();

    @Column(name="category")
    private String category;

    @Column(name="post_title")
    private String postTitle;

    @Column(name="contents")
    private String contents;

    @Column(name="read_count")
    private Integer readCount;

    @Column(name="modi_date")
    private LocalDate modiDate;

    @Column(name="notice_yn")
    private Boolean noticeYn;

    @Column(name="delete_yn")
    private Boolean deleteYn;

    @Column(name="comment")
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="rating_id")
    private Rating rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
