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
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


interface CustomAdminRepository {

     Map<String,Long> localTotalChart();
     Map<String,Long> userLocalGenderChart(String localSelect);
     Map<String,Integer> userAgeChart(String localSelect);
     Map<?,?> joinDateChart(LocalDate joinStartDate,LocalDate joinEndDate);
    Long storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeLocal();
    List<SalesVoucher> salesMonthChart();
    Map<String,SalesVoucher> voucherNameChart(String voucherName,String  start,String end);
    Map<String,Integer>  useLocalChart(String localName,LocalDate startDate,LocalDate endDate);
    Map<String,Long> voucherSalesTotalChart();
    Map<String,Integer> useTotalLocalChart();


}


public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomAdminRepository {


    private final JPAQueryFactory query;
    private final UserRepository userRepository;

    public AdminRepositoryImpl(JPAQueryFactory query, UserRepository userRepository) {
        super(Admin.class);
        this.query = query;
        this.userRepository = userRepository;
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
    public List<SalesVoucher> salesMonthChart() {
        return  query.select(Projections.fields
                (SalesVoucher.class,sales.unitPrice.sum().as("unitPrice"),sales.salesDate))
                .from(sales).groupBy(sales.salesDate.month()).fetch();
    }




    @Override
    public Map<String,SalesVoucher> voucherNameChart(String voucherName,String start,String end) {

        Map<String,SalesVoucher> voucherMap = new HashMap<>();

        int startDate = Integer.parseInt(start);
        int endDate = Integer.parseInt(end);

        for(int i= startDate;i<=endDate;i++){
            SalesVoucher monthTotal=  query.select(Projections.fields(SalesVoucher.class,sales.unitPrice.sum().as("unitPrice"),
                    localCurrencyVoucher.localCurrencyName))
                    .from(sales).where(sales.salesDate.stringValue().substring(0,7).like("2020-"+"%"+i)
                            .and(localCurrencyVoucher.localCurrencyName.like(voucherName+"%"))).fetchOne();

            if(monthTotal.getUnitPrice()!=0){
                voucherMap.put("2020-"+i,monthTotal);
            }

        }


        return voucherMap;

    }



    @Override
    public Map<String,Integer> useLocalChart( String localName,LocalDate startDate,LocalDate endDate) {



        Map<String,Integer> result = new HashMap<>();

                Integer useTest = query.select(sales.unitPrice.sum())
                        .from(sales)
                        .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                        .where(sales.useDate.between(startDate,endDate)
                                .and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%"))
                        ).fetchOne();

                        result.put("사용 완료",useTest);

                 Integer useCancel = query.select(sales.unitPrice.sum())
                                    .from(sales)
                                    .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                                    .where(sales.cancelDate.between(startDate,endDate)
                                            .and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%"))).fetchOne();

                    result.put("취소 완료",useCancel);

//                 Integer unUse = query.select(sales.unitPrice.sum())
//                                .from(sales)
//                                .join(sales.localCurrencyVoucher,localCurrencyVoucher)
//                                .where(sales.useDate.between(startDate,endDate).isNull()
//                                .and(sales.cancelDate.between(startDate,endDate).isNull())
//                                .and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%")))
//                                .fetchOne();
//                                .where((sales.useDate.between(startDate,endDate).isNull())
//                                        .and(sales.cancelDate.between(startDate,endDate).isNull())
//                                        .and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%")))
//                                        .fetchOne();.and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%")))

            /*

SELECT a.cancel_date,a.use_date,b.local_currency_name,a.sales_date,a.unit_price FROM sales a INNER Join local_currency_voucher b
 ON a.local_currency_voucher_id = b.local_currency_voucher_id where a.use_date
 BETWEEN 2020-07-01 AND 2020-07-31 IS NULL AND a.cancel_date  BETWEEN 2020-07-01 AND 2020-07-31 IS NULL
 AND b.local_currency_name LIKE '%고양%';
             */
//            result.put("미사용",unUse);




        return result;
    }

    @Override
    public Map<String, Long> voucherSalesTotalChart() {

        Map<String,Long> voucherSales = new HashMap<>();
        String[] local ={"연천", "포천", "파주", "동두천", "양주", "의정부", "가평", "고양",
                "김포", "남양주", "구리", "하남", "양평", "광주", "여주", "이천", "용인", "안성",
                "평택", "화성", "수원", "오산", "안산", "군포", "의왕", "안양", "과천", "부천",
                "광명", "성남", "시흥"};



        for(int i=0;i<local.length;i++){
        Long result=  query.select(Projections.fields(SalesVoucher.class,sales.unitPrice.sum().as("unitPrice"),
                    localCurrencyVoucher.localCurrencyName))
                    .from(sales).innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher).where(localCurrencyVoucher.localCurrencyName.like(local[i]+"%"))
                    .fetchCount();

        voucherSales.put(local[i]+"사랑상품권",result);
        }



        return voucherSales;
    }



    @Override
    public Map<String,Integer> useTotalLocalChart() {

        String[] useYn = {"사용완료","취소완료","미사용"};

        Map<String,Integer> result = new HashMap<>();


        for (String s : useYn) {
            Integer useTest = query.select(sales.unitPrice.sum())
                    .from(sales)
                    .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                    .where(sales.currencyState.like("%"+s+"%")).fetchOne();
            result.put(s,useTest);

        }


        return result;
    }


}
