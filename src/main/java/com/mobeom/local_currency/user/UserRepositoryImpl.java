package com.mobeom.local_currency.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

interface UserRepository extends JpaRepository<User, Long>, UserService {}

interface UserService {

}

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserService {
    @Autowired
    JPAQueryFactory query;

    UserRepositoryImpl() {
        super(User.class);
    }
}
