package com.mobeom.local_currency.favorites;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface CustomFavoritesRepository {

}

public class FavoritesRepositoryImpl extends QuerydslRepositorySupport implements CustomFavoritesRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    FavoritesRepositoryImpl() {
        super(Favorites.class);
    }
}
