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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
interface CustomAdminRepository {
    public List<User> List(String searchWord);
    public Map<String,Long> chart();
    public Map<String,Long> userLocalChart(String localSelect);
}

@Component
public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomAdminRepository {
        //@Autowired AdminRepository adminRepository;

    public AdminRepositoryImpl() { super(Admin.class);}

    @Autowired JPAQueryFactory query;

    @Override
    public List<User> List(String searchWord) {
        QUser user = QUser.user;

        List<User> list = new ArrayList<>();

        if(!searchWord.equals("null")) {
            list = query.select(Projections.fields(User.class,user.userId,user.email,user.gender,user.name)).from(user).where(user.userId.like("%" + searchWord + "%")).fetch();
        }else {
            list = query.select(Projections.fields(User.class,user.userId,user.email,user.gender,user.name)).from(user).fetch();
        }


        return list;
    }

   @Override
    public Map<String,Long> chart() {
       QUser user = QUser.user;
       String[] local ={"연천", "포천", "파주", "동두천", "양주", "의정부", "가평", "고양",
               "김포", "남양주", "구리", "하남", "양평", "광주", "여주", "이천", "용인", "안성",
               "평택", "화성", "수원", "오산", "안산", "군포", "의왕", "안양", "과천", "부천",
               "광명시", "성남시", "시흥시"};

        Map<String,Long> localChart = new HashMap<>();

        for(int i=0;i<local.length;i++){
            Long num = query.selectFrom(user).where(user.defaultAddr.like("%"+local[i]+"%")).fetchCount();
            localChart.put(local[i],num);
        }

       System.out.println(localChart);



       return localChart;
    }

    @Override
    public Map<String, Long> userLocalChart(String localSelect) {

        QUser user = QUser.user;
        Map<String,Long> userLocal = new HashMap<>();


        Long man = query.selectFrom(user).where(user.defaultAddr.like("%"+localSelect+"%").and(user.gender.like("M"))).fetchCount();
        Long woman =  query.selectFrom(user).where(user.defaultAddr.like("%"+localSelect+"%").and(user.gender.like("F"))).fetchCount();
        

        userLocal.put("남",man);
        userLocal.put("여",woman);
        return userLocal;
    }


}
