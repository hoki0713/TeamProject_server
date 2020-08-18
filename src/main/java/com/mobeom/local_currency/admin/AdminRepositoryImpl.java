package com.mobeom.local_currency.admin;

import static com.mobeom.local_currency.rating.QRating.rating;
import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.user.QUser.user;
import static com.mobeom.local_currency.sales.QSales.sales;
import static com.mobeom.local_currency.voucher.QLocalCurrencyVoucher.localCurrencyVoucher;
import static com.mobeom.local_currency.reportList.QReportList.reportList;

import com.mobeom.local_currency.join.ReportListStore;
import com.mobeom.local_currency.join.SalesVoucherUser;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.user.UserRepository;
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
    Map<String,Long> storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeLocal();
    List<SalesVoucherUser> salesMonthChart();
    Map<String, SalesVoucherUser> voucherNameChart(String voucherName, String  start, String end);
    Map<String,Integer>  useLocalChart(String localName,LocalDate startDate,LocalDate endDate);
    Map<String,Long> voucherSalesTotalChart();
    Map<String,Integer> useTotalLocalChart();
    Map<String, List<SalesVoucherUser>> salesList();
    Map<String,List<ReportListStore>> reportList();
    ReportListStore getOneStore(Long id);
    List<SalesVoucherUser> salesListSearch();
    List<Store> storeSearch(String searchWord);


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



        Map<String,Long> localChart = new HashMap<>();

       LocalName[] locals = LocalName.values();

        for(LocalName i : locals){

            if(i.toString().equals("양주")) {
                Long num = query.selectFrom(user)
                        .where(user.defaultAddr.like("경기도 " +i + "%"))
                    .fetchCount();
                localChart.put(i.toString(),num);
            } else {
                Long num = query.selectFrom(user)
                        .where(user.defaultAddr.like("%" + i + "%"))
                        .fetchCount();
                localChart.put(i.toString(),num);
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
    public Map<String,Long> storeLocalsChart(String localSelect) {


        Map<String,Long> storeType = new HashMap<>();

        IndustryType[] industryType = IndustryType.values();

        for(IndustryType type: industryType){
            Long result = query.selectFrom(store)
                    .where(store.mainCode.like("%"+type+"%").and(store.localName.like("%"+localSelect+"%"))).fetchCount();
            storeType.put(type.toString()+"업",result);
        }

        return storeType;

    }



    @Override
    public Map<String, Long> storeTypeLocal() {

    Map<String,Long> storeResult = new HashMap<>();


    IndustryType[] industryType = IndustryType.values();

    for(IndustryType type: industryType){
        Long result = query.selectFrom(store)
                .where(store.mainCode.like(type+"%")).fetchCount();

        storeResult.put(type.toString()+"업",result);
    }

        return storeResult;
    }

    @Override
    public List<SalesVoucherUser> salesMonthChart() {
        return  query.select(Projections.fields
                (SalesVoucherUser.class,sales.unitPrice.sum().as("unitPrice"),sales.salesDate))
                .from(sales).groupBy(sales.salesDate.month()).fetch();
    }




    @Override
    public Map<String, SalesVoucherUser> voucherNameChart(String voucherName, String start, String end) {

        Map<String, SalesVoucherUser> voucherMap = new HashMap<>();

        int startDate = Integer.parseInt(start);
        int endDate = Integer.parseInt(end);

        for(int i= startDate;i<=endDate;i++){
            SalesVoucherUser monthTotal=  query.select(Projections.fields(SalesVoucherUser.class,sales.unitPrice.sum().as("unitPrice"),
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
                                .and(localCurrencyVoucher.localCurrencyName.like(localName+"%"))
                        ).fetchOne();


                        result.put("사용 완료",useTest);

                 Integer useCancel = query.select(sales.unitPrice.sum())
                                    .from(sales)
                                    .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                                    .where(sales.cancelDate.between(startDate,endDate)
                                            .and(localCurrencyVoucher.localCurrencyName.like(localName+"%"))).fetchOne();

                    result.put("취소 완료",useCancel);

//                 Integer unUse = query.select(sales.unitPrice.sum())
//                                .from(sales)
//                                .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
//                                .where(sales.useDate.between(startDate,endDate).isNull()
//                                    .and(sales.cancelDate.between(startDate,endDate).isNull())
//                                    .and(localCurrencyVoucher.localCurrencyName.like("%"+localName+"%")))
//                                .fetchOne();

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

        LocalName[] locals = LocalName.values();


        for(LocalName i : locals){
        Long result=  query.select(Projections.fields(SalesVoucherUser.class,sales.unitPrice.sum().as("unitPrice"),
                    localCurrencyVoucher.localCurrencyName))
                    .from(sales).innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher).where(localCurrencyVoucher.localCurrencyName.like(i+"%"))
                    .fetchCount();

        voucherSales.put(i+"사랑상품권",result);

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

    @Override
    public Map<String, List<SalesVoucherUser>> salesList() {
        Map<String,List<SalesVoucherUser>> list = new HashMap<>();

      List<SalesVoucherUser> result= query.select(Projections.fields(SalesVoucherUser.class,user.userId,user.name,
                sales.salesId,localCurrencyVoucher.localCurrencyName,sales.currencyState,sales.salesDate,
                sales.useDate,sales.cancelDate)).from(sales)
                .innerJoin(user).on(user.userId.like(sales.user.userId))
                .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                .on(localCurrencyVoucher.localCurrencyVoucherId.eq(sales.localCurrencyVoucher.localCurrencyVoucherId))
                .limit(100).fetch();

        list.put("sales",result);

        return list;
    }

    @Override
    public Map<String, List<ReportListStore>> reportList() {
        Map<String,List<ReportListStore>> result = new HashMap<>();

       List<ReportListStore> reportLists =query.select(Projections.fields(ReportListStore.class,
              store.id,store.storeName,store.address,store.mainCode
       ,store.storePhone)).from(reportList)
                .where(reportList.reportedCount.goe(3)).fetch();

       result.put("report",reportLists);
        return result;
    }

    @Override
    public ReportListStore getOneStore(Long id) {

        ReportListStore result = query.select(Projections.fields(ReportListStore.class,
                store.storePhone,store.mainCode,store.address
                ,store.storeName,store.id,store.storeType)).
                from(store)
                .where(store.id.eq(id)).fetchOne();

       List<ReportListStore> ratingResult = query.select(Projections.fields(ReportListStore.class,
                rating.starRating)).from(rating).where(rating.store.id.eq(id)).fetch();



        return result;
    }

    @Override
    public List<SalesVoucherUser> salesListSearch() {

        List<SalesVoucherUser> searchResult= query.select(Projections.fields(SalesVoucherUser.class,user.userId,user.name,
                sales.salesId,localCurrencyVoucher.localCurrencyName,sales.currencyState,sales.salesDate,
                sales.useDate,sales.cancelDate)).from(sales)
                .innerJoin(user).on(user.userId.like(sales.user.userId))
                .innerJoin(sales.localCurrencyVoucher,localCurrencyVoucher)
                .on(localCurrencyVoucher.localCurrencyVoucherId.eq
                        (sales.localCurrencyVoucher.localCurrencyVoucherId))
                .where(localCurrencyVoucher.localCurrencyName.like("%"+"고양"+"%")
                        .and(sales.currencyState.like("사용완료"))
                        .and(user.userId.like("%"+"%"))).fetch();
        System.out.println("나오는지"+searchResult.toString());
        return searchResult;
    }

    @Override
    public List<Store> storeSearch(String searchWord) {
        List<Store> storeSearchResult = query.select(store).from(store)
                .where(store.storeName.like("%"+searchWord+"%")).fetch();
        return storeSearchResult;
    }


}
