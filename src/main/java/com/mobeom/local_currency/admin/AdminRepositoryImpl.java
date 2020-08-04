package com.mobeom.local_currency.admin;



import com.mobeom.local_currency.user.QUser;
import com.mobeom.local_currency.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
interface CustomAdminRepository {
     List<User> List(String searchWord);
     Map<String,Long> chart();
     Map<String,Long> userLocalChart(String localSelect);
     Map<String,Integer> userAgeChart(String localSelect);
     Map<?,?> joinDateChart(LocalDate joinStartDate,LocalDate joinEndDate);
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
               "광명시", "성남시", "시흥시"}; //enum으로처리


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
        Map<String, Long> userLocal = new HashMap<>();




        Long man = query.selectFrom(user).where(user.defaultAddr.like("%"+localSelect+"%").and(user.gender.like("M"))).fetchCount();
        Long woman =  query.selectFrom(user).where(user.defaultAddr.like("%"+localSelect+"%").and(user.gender.like("F"))).fetchCount();






//        list.forEach((one) -> {
//            String[] strDate = one.toString().split("-");
//            int year = Integer.parseInt(strDate[0]);
//            int b=date-year;
//
//
//
//            System.out.println(b);
//
//        });




        userLocal.put("남",man);
        userLocal.put("여",woman);

        return userLocal;
    }

    @Override
    public Map<String, Integer> userAgeChart(String localSelect) {

        QUser user = QUser.user;

        Map<String,Integer> userAge = new HashMap<>();

        String formatDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));




        int date = Integer.parseInt(formatDate);




        List<?> list = new ArrayList<>();

        list=query.select(user.birthDate).from(user).where(user.defaultAddr.like("%" +localSelect +"%")).fetch();

        int ten=0,twenties=0,thirties=0,forties=0,fifties=0,sixties=0,old=0;

        for(int i=0;i<list.size();i++){
            String[] birthdayYear =list.get(i).toString().split("-");
            int year = Integer.parseInt(birthdayYear[0]);
            int age = date-year;

            switch (age/10){
                case 0 : case 1: ten+=1; break;
                case 2: twenties+=1; break;
                case 3: thirties+=1; break;
                case 4: forties+=1; break;
                case 5: fifties+=1; break;
                case 6: sixties+=1; break;
                default: old+=1;break;
            }

        }

       // System.out.println("모든숫자의 합:"+(ten+twenties+thirties+forties+fifties+sixties+old));


        userAge.put("ten",ten);
        userAge.put("twenties",twenties);
        userAge.put("thirties",thirties);
        userAge.put("forties",forties);
        userAge.put("fifties",fifties);
        userAge.put("sixties",sixties);
        userAge.put("old",old);




        return userAge;
    }

    @Override
    public Map<?, ?> joinDateChart(LocalDate joinStartDate,LocalDate joinEndDate) { //객체 vo 달 /mm값 으로요
        QUser user =QUser.user;


        for(int i=1;i<=3;i++){
            Long a =query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinStartDate.plusMonths(i))).fetchCount();
            List<?> userList = query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinStartDate.plusMonths(i))).fetch();
            System.out.println("userList for문안=>"+i+"/"+userList.toString());
            System.out.println("a=>"+i+"/"+a.toString());
        }

        Long b =query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinEndDate)).fetchCount();
        List<?> userList = query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinEndDate)).fetch();
        System.out.println("userList for문 밖=>"+userList.toString());

        System.out.println("b=>"+b.toString());



       // List<?> userList = query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinStartDate.plusMonths(1))).fetch();
       // System.out.println("userList=>"+userList.toString());

        return null;
    }




}
