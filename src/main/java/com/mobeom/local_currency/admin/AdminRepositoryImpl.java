package com.mobeom.local_currency.admin;
/*
전체 지역별xx

지역별로 나눠서 업종으로 세분화

-사용여부
지역 다띄울지
아니면
지역정해서 사용얼마 미사용얼마 띄울지

 */

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.user.QUser.user;
import static com.mobeom.local_currency.sales.QSales.sales;
import static com.mobeom.local_currency.voucher.QLocalCurrencyVoucher.localCurrencyVoucher;

import com.mobeom.local_currency.sales.Sales;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;



interface CustomAdminRepository {

     Map<String,Long> localTotalChart();
     Map<String,Long> userLocalGenderChart(String localSelect);
     Map<String,Integer> userAgeChart(String localSelect);
     Map<?,?> joinDateChart(LocalDate joinStartDate,LocalDate joinEndDate);
     //List<Store> findAll();
    Long storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeLocal();
    List<Integer> currencyChart();
    Integer test();

}


public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomAdminRepository {


    private final JPAQueryFactory query;

    public AdminRepositoryImpl(JPAQueryFactory query) {
        super(Admin.class);
        this.query = query;
    }




   @Override
    public Map<String,Long> localTotalChart() {

       String[] local ={"연천", "포천", "파주", "동두천", "양주", "의정부", "가평", "고양",
               "김포", "남양주", "구리", "하남", "양평", "광주", "여주", "이천", "용인", "안성",
               "평택", "화성", "수원", "오산", "안산", "군포", "의왕", "안양", "과천", "부천",
               "광명시", "성남시", "시흥시"}; //enum으로처리

        Map<String,Long> localChart = new HashMap<>();

        for(int i=0;i<local.length;i++){
            Long num = query.selectFrom(user)
                    .where(user.defaultAddr.like("%"+ local[i] +"%"))
                    .fetchCount();
            localChart.put(local[i],num);
        }



       return localChart;
    }

    @Override
    public Map<String, Long> userLocalGenderChart(String localSelect) {


        Map<String, Long> userLocal = new HashMap<>();

        Long man = query.selectFrom(user)
                .where(user.defaultAddr.like("%"+localSelect+"%")
                .and(user.gender.like("M")))
                .fetchCount();
        Long woman =  query.selectFrom(user)
                .where(user.defaultAddr.like("%"+localSelect+"%")
                .and(user.gender.like("F")))
                .fetchCount();

        userLocal.put("남",man);
        userLocal.put("여",woman);

        return userLocal;
    }

    @Override
    public Map<String, Integer> userAgeChart(String localSelect) {


        Map<String,Integer> userAge = new HashMap<>();

        String formatDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        int date = Integer.parseInt(formatDate);

        List<LocalDate> list  = query.select(user.birthDate)
                .from(user)
                .where(user.defaultAddr.like("%" +localSelect +"%"))
                .fetch();

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

        LocalDate fixedEndDate = joinStartDate.plusMonths(1).minusDays(1);
        for(int i=1;i<=3;i++){
            Long a =query.selectFrom(user)
                    .where(user.joinDate.between(joinStartDate,joinStartDate.plusDays(30)))
                    .fetchCount();

            List<?> userList = query.selectFrom(user)
                    .where(user.joinDate.between(joinStartDate,joinStartDate.plusMonths(i)))
                    .fetch();

        }

        Long b =query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinEndDate)).fetchCount();
        List<?> userList = query.selectFrom(user).where(user.joinDate.between(joinStartDate,joinEndDate)).fetch();

        return null;
    }



    @Override
    public Long storeLocalsChart(String localSelect) {
//        Map<String,Long> localStoreChart = new HashMap<>();
//
//        Long num = query.selectFrom(store)
//                .where(store.address.like("%"+localSelect+"%"))
//                .fetchCount();


        //localStoreChart.put("local",num);

        //return localStoreChart;
        return query.selectFrom(store)
                .where(store.address.like("%"+localSelect+"%"))
                .fetchCount();

    }



    @Override
    public Map<String, Long> storeTypeLocal() {

        Map<String,Long> storeType = new HashMap<>();


        Long storeNum= query.selectFrom(store).where(store.storeTypeCode.like("3099").and(store.address.like("%"+"고양"+"%"))).fetchCount();
        List<?> list = query.selectFrom(store).where(store.storeTypeCode.like("3099").and(store.address.like("%"+"고양"+"%"))).fetch();

        System.out.println("storenum-"+storeNum);
        System.out.println(list.toString());

        storeType.put("a",storeNum);

        return storeType;
    }

    @Override
    public List<Integer> currencyChart() {
        return query.select(sales.unitPrice.sum()).from(sales).groupBy(sales.salesDate.month()).fetch();
    }

    @Override
    public Integer test() {
     Integer list= query.select(sales.unitPrice.sum())
               .from(sales)
               .innerJoin(sales.localCurrencyVoucher, localCurrencyVoucher)
               .groupBy(localCurrencyVoucher.localName)
               .having(localCurrencyVoucher.localName.like("%"+"가평"+"%")).fetchOne();

        return list;

    }


}
