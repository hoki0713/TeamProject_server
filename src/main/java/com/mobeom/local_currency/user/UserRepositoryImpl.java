package com.mobeom.local_currency.user;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
interface CustomedUserRepository {
    Optional<User> findByUserId(String userId);

}

public class UserRepositoryImpl extends QuerydslRepositorySupport implements CustomedUserRepository  {
    @Autowired
    JPAQueryFactory queryFactory;

    UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        QUser qUser = QUser.user;
        User findOne = queryFactory.selectFrom(qUser).where(qUser.userId.eq(userId)).fetchOne();
        return Optional.ofNullable(findOne);
    }

}

