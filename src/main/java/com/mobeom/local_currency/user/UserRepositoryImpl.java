package com.mobeom.local_currency.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface CustomUserRepository {
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserNameAndEmail(String name, String email);

    Optional<User> findByUserIdNameEmail(String userId, String name, String email);

    List<User> findByUserName(String searchWord);
}

public class UserRepositoryImpl extends QuerydslRepositorySupport implements CustomUserRepository {
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

    @Override
    public Optional<User> findByUserNameAndEmail(String name, String email) {
        QUser qUser = QUser.user;
        User findOne = queryFactory.selectFrom(qUser).where(qUser.name.eq(name), qUser.email.eq(email)).fetchOne();
        return Optional.ofNullable(findOne);
    }

    @Override
    public Optional<User> findByUserIdNameEmail(String userId, String name, String email) {
        QUser qUser = QUser.user;
        User findOne = queryFactory.selectFrom(qUser).where(qUser.userId.eq(userId), qUser.name.eq(name), qUser.email.eq(email)).fetchOne();
        return Optional.ofNullable(findOne);
    }

    @Override
    public List<User> findByUserName(String searchWord) {
        QUser qUser = QUser.user;
        List<User> users =
                queryFactory.selectFrom(qUser).where(qUser.name.like(searchWord)).fetch();
        return users;
    }

}

