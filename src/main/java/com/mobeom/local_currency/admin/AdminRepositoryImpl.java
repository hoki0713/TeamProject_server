package com.mobeom.local_currency.admin;



import com.mobeom.local_currency.user.QUser;
import com.mobeom.local_currency.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
interface CustomedAdminRepository {
    public List<User> List();
}

@Component
public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomedAdminRepository {


    public AdminRepositoryImpl() { super(Admin.class);}

    @Override
    public List<User> List() {
        QUser user = QUser.user;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        List<User> list = new ArrayList<>();

        list = query.select(Projections.fields(User.class,user.userId,user.email,user.gender,user.name)).from(user).fetch();





        return list;
    }


}
