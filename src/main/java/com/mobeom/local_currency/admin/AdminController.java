package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.join.ReportListStore;
import com.mobeom.local_currency.join.SalesVoucherUser;
import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.reportList.ReportList;
import com.mobeom.local_currency.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class AdminController {

    private final AdminRepository adminRepository;
    private final AdminService adminService;
    private final Box box;


    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUserList() {
        List<User> userList = adminService.getAllUserList();
        return ResponseEntity.ok(userList);
    }


    @GetMapping("/list/{userId}")
    public User getOneUser(@PathVariable String userId) {
        Optional<User> findOne = adminService.findOneUser(userId);
        return findOne.orElse(null);
    }


    @GetMapping("/userTotal-chart/{localSelect}")
    public Map<?, ?> userLocalGenderChart(@PathVariable String localSelect) {

        Map<String, Long> testChart1 = adminService.userLocalGenderChart(localSelect);
        Map<String, Integer> testChart2 = adminService.userAgeTotal(localSelect);
        box.put("gender", testChart1);
        box.put("age", testChart2);

        return box.get();
    }

    @GetMapping("/chart/ratio-of-user-region")
    public Map<String, Long> ratioOfUserRegion() {
        return adminService.localTotalChart();
    }

    @GetMapping("/userAge-chart/{localSelect}")
    public Map<String, Integer> userAgeChart(@PathVariable String localSelect) {
        Map<String, Integer> userAge = adminService.userAgeTotal(localSelect);

        return userAge;
    }


    @GetMapping("/storeTypeChart")
    public Map<String, Long> storeTypeChart() {
        return adminService.storeTypeChart();
    }


    @GetMapping("/currency/month/total")
    public ResponseEntity<Map<String, Integer>> currencySalesMonthTotalChart() {
        Map<String, Integer> result = new TreeMap<>();
        List<SalesVoucherUser> monthList = adminService.salesMonthChart();
        monthList.forEach(sales -> {
            String year = sales.getSalesDate().toString().split("-")[0];
            String month = sales.getSalesDate().toString().split("-")[1];
            result.put(year + "-" + month, sales.getUnitPrice());
        });
        return ResponseEntity.ok(result);
    }

    @GetMapping("/voucher/sales-total")
    public Map<String, SalesVoucherUser> voucherSalesTotalChart() {
        return adminService.voucherSalesTotalChart();
    }

    @GetMapping("/voucher/name-list/{currencyName}/{startDate}/{endDate}")
    public Map<String, SalesVoucherUser> voucherNameChart(@PathVariable String currencyName, @PathVariable String startDate, @PathVariable String endDate) { //SalesVoucher voucherNameChart(String voucherName)

        String start = startDate.split("-")[1];
        String end = endDate.split("-")[1];

        return adminService.voucherNameChart(currencyName, start, end);
    }


    @GetMapping("/useChart/total")
    public Map<String, Integer> useChartTotal() {
        return adminRepository.useTotalLocalChart();
    }

    @GetMapping("/useChart/test/{localName}/{startDate}/{endDate}")
    public Map<String, Integer> useLocalChart(@PathVariable String localName, @PathVariable String startDate, @PathVariable String endDate) {

        LocalDate start_date = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end_date = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        return adminService.useLocalChart(localName, start_date, end_date);
    }

    // Emilia code
    @GetMapping("/userList/{pageNumber}")
    public ResponseEntity<UserPageVO> getAllUsers(@PathVariable int pageNumber) {
        UserPageVO userList = adminService.getUserPage(pageNumber);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/searchUserList/{selectedOption}/{searchWord}/{pageNumber}")
    public ResponseEntity<UserPageVO> getSearchedUsers(@PathVariable String selectedOption,
                                                       @PathVariable String searchWord) {
        UserPageVO userList = adminService.getSearchedUsers(selectedOption, searchWord);
        return ResponseEntity.ok(userList);
    }


    @GetMapping("/store/chart-local/{localSelect}")
    public Map<String, Long> storeLocalChart(@PathVariable String localSelect) {
        return adminService.storeLocalsChart(localSelect);
    }

    @GetMapping("/store/chart-all")
    public Map<String, ?> storeIndustryChartAll() {
        box.put("storeTotalCount",adminService.getStoreCount());
        box.put("storeChartAll",adminService.storeIndustryChartAll());
        return box.get();
    }

    @GetMapping("/sales/list/{pageNumber}")
    public Map<String, ?> salesList(@PathVariable int pageNumber) {
        SalesPageVO salesList = adminService.salesList(pageNumber);
        box.put("salesList", salesList);
        box.put("salesCount", adminService.getSalesCount());
        return box.get();
    }

    @GetMapping("/report/list")
    public Map<String, List<ReportListStore>> reportList() {

        return adminService.reportList();
    }

    @GetMapping("/store/detail/{id}")
    public ReportListStore storeOne(@PathVariable String id) {
        return adminService.oneStore(Long.parseLong(id));
    }

    @GetMapping("/store/report/initialization/{id}")
    public ReportList reportInitial(@PathVariable String id) {
        Optional<ReportList> result = adminService.oneStoreReport(Long.parseLong(id));
        ReportList updateReport = result.get();
        updateReport.setReportedCount(0);
        adminService.updateInitial(updateReport);
        return updateReport;
    }

    @GetMapping("/sales/search")
    public Map<String, List<SalesVoucherUser>> salesSearch(@RequestParam("useStatusSelect") String useStatus,
                                                           @RequestParam("citySelect") String citySelect,
                                                           @RequestParam("searchWord") String searchWord) {

        Map<String, List<SalesVoucherUser>> result = new HashMap<>();
        result.put("sales", adminRepository.salesListSearch(useStatus, citySelect, searchWord));
        return result;

    }

    @GetMapping("/store/search/{searchWord}")
    public List<ReportListStore> storeSearch(@PathVariable String searchWord) {
        return adminService.storeSearch(searchWord);
    }
}
