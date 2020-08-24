package com.mobeom.local_currency.favorites;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


interface CustomFavoritesRepository {

    List<Favorites> findAllByUserId(long userId);

}

public class FavoritesRepositoryImpl extends QuerydslRepositorySupport implements CustomFavoritesRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    FavoritesRepositoryImpl() {
        super(Favorites.class);
    }

    @Override
    public List<Favorites> findAllByUserId(long userId) {
        QFavorites qFavorites = QFavorites.favorites;
        List<Favorites> favorites = queryFactory.selectFrom(qFavorites).where(qFavorites.user.id.eq(userId)).fetch();
        return favorites;
    }


}

