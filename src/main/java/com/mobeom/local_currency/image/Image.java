package com.mobeom.local_currency.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.board.Board;
import com.mobeom.local_currency.post.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id", nullable = false)
    private Long imageId;

    @Column(name="image_url", nullable=false)
    private String imageUrl;

    @CreationTimestamp
    @Column(name="update_date", nullable=false)
    private DateTime updateDate;

    @Column(name="image_cate", nullable=false)
    private String imageCate;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="post_id")
    @Column(name="post_id", nullable=false)
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="board_id")
    @Column(name="board_id", nullable=false)
    private Board board;
}
