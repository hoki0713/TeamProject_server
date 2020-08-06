package com.mobeom.local_currency.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface CustomPostRepository {

}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    PostRepositoryImpl() {
        super(Post.class);
    }


}
