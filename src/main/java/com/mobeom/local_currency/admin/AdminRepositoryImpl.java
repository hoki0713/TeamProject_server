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

import com.mobeom.local_currency.join.SalesVoucher;
import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



interface CustomAdminRepository {

     Map<String,Long> localTotalChart();
     Map<String,Long> userLocalGenderChart(String localSelect);
     Map<String,Integer> userAgeChart(String localSelect);
     Map<?,?> joinDateChart(LocalDate joinStartDate,LocalDate joinEndDate);
    Long storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeLocal();
    List<Sales> salesMonthChart();
    SalesVoucher voucherNameChart(String voucherName);
    Integer useChart(String useSelect , String localName);


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
               "광명", "성남", "시흥"}; //enum으로처리

        Map<String,Long> localChart = new HashMap<>();

        for(int i=0;i<local.length;i++){
            if(local[i].equals("양주")) {
                Long num = query.selectFrom(user)
                        .where(user.defaultAddr.like("경기도 " + local[i] + "%"))
                    .fetchCount();
                localChart.put(local[i],num);
            } else {
                Long num = query.selectFrom(user)
                        .where(user.defaultAddr.like("%" + local[i] + "%"))
                        .fetchCount();
                localChart.put(local[i],num);
            }

        }





       return localChart;
    }

    @Override
    public Map<String, Long> userLocalGenderChart(String localSelect) {


        Map<String, Long> userLocal = new HashMap<>();
        if(localSelect.equals("null")){
            Long man = query.selectFrom(user)
                            .where(user.gender.like("M"))
                    .fetchCount();
            Long woman = query.selectFrom(user)
                    .where(user.gender.like("F"))
                    .fetchCount();
            userLocal.put("남",man);
            userLocal.put("여",woman);
        }else{
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
        }




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

        List<LocalDate> userTotalList = query.select(user.birthDate)
                .from(user)
                .fetch();

        int ten=0,twenties=0,thirties=0,forties=0,fifties=0,sixties=0,old=0;
        if(localSelect.equals("null")){
            for(int i=0;i<userTotalList.size();i++){
                String[] birthdayYear =userTotalList.get(i).toString().split("-");
                int year = Integer.parseInt(birthdayYear[0]);
                int age = date-year;

                switch (age/10){
                    case 0 : case 1: ten+=1; break;
                    case 2: twenties+=1; break;
                    case 3: thirties+=1; break;
                    case 4: forties+=1; break;
                    case 5: fifties+=1; break;
                    case 6: sixties+=1; break;
                    case 7 : case 8: case 9: old+=1;break;
                }

            }
            userAge.put("10대",ten);
            userAge.put("20대",twenties);
            userAge.put("30대",thirties);
            userAge.put("40대",forties);
            userAge.put("50대",fifties);
            userAge.put("60대",sixties);
            userAge.put("70대이상",old);
        }else{
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
                    case 7 : case 8: case 9: old+=1;break;
                }

            }
            userAge.put("10대",ten);
            userAge.put("20대",twenties);
            userAge.put("30대",thirties);
            userAge.put("40대",forties);
            userAge.put("50대",fifties);
            userAge.put("60대",sixties);
            userAge.put("70대이상",old);
        }


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
    public List<Sales> salesMonthChart() {
        return  query.select(Projections.fields(Sales.class,sales.unitPrice.sum().as("unitPrice"),sales.salesDate))
                .from(sales).groupBy(sales.salesDate.month()).fetch();
    }




    @Override //이거는 뭐냐 거..그니까 기간이 들어가서 기간별로 뽑아내고싶음
    public SalesVoucher voucherNameChart(String voucherName) {
     SalesVoucher list= query.select(Projections.fields(SalesVoucher.class,sales.unitPrice.sum().as("unitPrice"),localCurrencyVoucher.localCurrencyName))
               .from(sales)
               .innerJoin(sales.localCurrencyVoucher, localCurrencyVoucher)
               .groupBy(localCurrencyVoucher.localName)
               .having(localCurrencyVoucher.localName.like("%"+voucherName+"%")).fetchOne();

        return list;

    }

    /*

SELECT SUM(unit_price),local_currency_voucher.local_name FROM sales inner JOIN local_currency_voucher ON sales.local_currency_voucher_id = local_currency_voucher.local_currency_voucher_id
WHERE sales.currency_state LIKE '%사용완료%' AND local_currency_voucher.local_name LIKE '%가평%'
     */
    @Override
    public Integer useChart(String useSelect , String localName) {
        Integer useTest = query.select(sales.unitPrice.sum())
                        .from(sales)
                        .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                        .where(sales.currencyState.like("%"+useSelect+"%").and(localCurrencyVoucher.localName.like("%"+localName+"%"))).fetchOne();


        return useTest;
    }




}
