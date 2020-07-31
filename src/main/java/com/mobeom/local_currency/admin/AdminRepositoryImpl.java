package com.mobeom.local_currency.admin;



import com.mobeom.local_currency.user.QUser;
import com.mobeom.local_currency.user.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
interface CustomedAdminRepository {
    public List<User> List(String searchWord);
    public List<Long> chart();
}

@Component
public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomedAdminRepository {
        //@Autowired AdminRepository adminRepository;

    public AdminRepositoryImpl() { super(Admin.class);}

    @Override
    public List<User> List(String searchWord) {
        QUser user = QUser.user;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        List<User> list = new ArrayList<>();

        if(!searchWord.equals("null")) {
            list = query.select(Projections.fields(User.class,user.userId,user.email,user.gender,user.name)).from(user).where(user.userId.like("%" + searchWord + "%")).fetch();
        }else {
            list = query.select(Projections.fields(User.class,user.userId,user.email,user.gender,user.name)).from(user).fetch();
        }


        return list;
    }

   @Override
    public List<Long> chart() {

       String[] local ={"김포","용인","양평"};
        QUser user = QUser.user;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        List<Long> localChart = new ArrayList<>();

        for(int i=0;i<local.length;i++){
            localChart.add(query.from(user).select(user).where(user.defaultAddr.like("%"+local[i]+"%")).fetchCount());

            //localch = query.from(user).select(user.count()).where(user.defaultAddr.like("%"+local[i]+"%")).fetch();
           //System.out.println(localChart);
                }

        //List<?> kimpo =query.from(user).select(user.count()).where(user.defaultAddr.like("%김포%")).fetch();



       return localChart;
    }


}
