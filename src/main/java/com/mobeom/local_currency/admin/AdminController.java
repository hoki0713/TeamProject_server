package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.join.SalesVoucher;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.user.User;
import com.querydsl.core.Tuple;
import org.mortbay.util.ajax.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final AdminRepository adminRepository;
    private final AdminService adminService;
    private final Box box;

    public AdminController(AdminRepository adminRepository, AdminService adminService, Box box) {
        this.adminRepository = adminRepository;
        this.adminService = adminService;
        this.box = box;
    }


    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUserList(){
        List<User> userList = adminService.getAllUserList();
        return ResponseEntity.ok(userList);
    }





    @GetMapping("/list/{userId}")
    public User getOneUser(@PathVariable String userId) {
        Optional<User> findOne = adminService.findOneUser(userId);
        return findOne.orElse(null);
       // return findOne.isPresent()? ResponseEntity.ok(findOne) : ResponseEntity.notFound().build();

    }
    


    @GetMapping("/userTotal-chart/{localSelect}")
    public Map<?,?> userLocalGenderChart(@PathVariable String localSelect){

        Map<String,Long> testChart1 = adminService.userLocalGenderChart(localSelect);
        Map<String,Integer> testChart2 = adminService.userAgeTotal(localSelect);
       box.put("gender",testChart1);
        box.put("age",testChart2);

        //return adminService.userLocalGenderChart(localSelect);
        return box.get();
    }

    @GetMapping("/chart/ratio-of-user-region")
    public Map<String,Long> ratioOfUserRegion(){
        return adminService.localTotalChart();
    }

    @GetMapping("/userAge-chart/{localSelect}")
    public Map<String,Integer> userAgeChart(@PathVariable String localSelect){
        Map<String,Integer> userAge = adminService.userAgeTotal(localSelect);

        return userAge;
    }

    @GetMapping("/joinDate-chart/{startDate}/{endDate}") //* 아직 구현 xx
    public void joinChart(@PathVariable String startDate, @PathVariable String endDate){
        LocalDate start_date = LocalDate.parse(startDate,DateTimeFormatter.ISO_DATE);
        LocalDate end_date = LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);
        adminService.joinChart(start_date,end_date);
    }

    @GetMapping("/localChart/{localSelect}")
    public Long storeLocalChart(@PathVariable String localSelect){
        return adminService.storeLocalsChart(localSelect);

    }


    @GetMapping("/storeTypeChart")
    public Map<String,Long> storeTypeChart(){
        return adminService.storeTypeChart();
    }


    @GetMapping("/currency/month/total")
    public ResponseEntity<Map<String,Integer>> currencySalesMonthTotalChart(){
        Map<String,Integer> result = new HashMap<>();
        List<SalesVoucher> monthList = adminService.salesMonthChart();
        monthList.forEach(sales->{
            String year=sales.getSalesDate().toString().split("-")[0];
            String month=sales.getSalesDate().toString().split("-")[1];
           result.put(year+"-"+month,sales.getUnitPrice());
        });
       return ResponseEntity.ok(result);
    }

    @GetMapping("/voucher/sales-total")
    public Map<String,Long> test (){
        return adminRepository.voucherSalesTotalChart();
    }

    @GetMapping("/voucher/name-list/{currencyName}/{startDate}/{endDate}")
    public Map<String,SalesVoucher> voucherNameChart(@PathVariable String currencyName,@PathVariable String startDate,@PathVariable String endDate){ //SalesVoucher voucherNameChart(String voucherName)

        String start = startDate.split("-")[1];
        String end = endDate.split("-")[1];
        System.out.println(startDate+"end"+endDate);
        System.out.println(currencyName);
        return adminService.voucherNameChart(currencyName,start,end);
    }

    @GetMapping("/useChart/test/{useSelect}/{localName}")
    public Integer test3(@PathVariable String useSelect, @PathVariable String localName){
        System.out.println(adminService.useChartTest(useSelect,localName));
        return adminService.useChartTest(useSelect,localName);
    }
}
