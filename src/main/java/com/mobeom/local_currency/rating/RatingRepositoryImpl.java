package com.mobeom.local_currency.rating;

import static com.mobeom.local_currency.rating.QRating.rating;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

interface CustomRatingRepository {

    Long findByUserId(String id);
}

@Repository
public class RatingRepositoryImpl implements CustomRatingRepository {
    private final JPAQueryFactory queryFactory;

    public RatingRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Long findByUserId(String id) {
        return queryFactory.select(rating.user.id).from(rating).where(rating.user.id.eq(
                Long.parseLong(id)
        )).fetchFirst();
    }
}
