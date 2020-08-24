package com.mobeom.local_currency.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.post.Post;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false) private Long ratingId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name= "star_rating", nullable = false)
    private int starRating;

    @OneToOne (mappedBy = "rating")
    @JoinColumn
    private Post post;

}
