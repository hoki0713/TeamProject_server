package com.mobeom.local_currency.admin;



import com.mobeom.local_currency.join.ReportListStore;
import com.mobeom.local_currency.join.SalesVoucherUser;
import com.mobeom.local_currency.reportList.ReportList;
import com.mobeom.local_currency.reportList.ReportListRepository;
import com.mobeom.local_currency.user.RequestedUsersVO;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface AdminService{
    
    Map<String,Long> localTotalChart();
    Map<String,Long> userLocalGenderChart(String localSelect);
    Map<String,Integer> userAgeTotal(String localSelect);

    Map<String,Long> storeTypeChart();
    List<SalesVoucherUser> salesMonthChart();
    List<User> getAllUserList();
    Optional<User> findOneUser(String userId);
    Map<String,Integer> useLocalChart(String localName,LocalDate startDate,LocalDate endDate);
    Map<String, SalesVoucherUser> voucherNameChart(String voucherName, String start, String end);
    Map<String,Long> storeLocalsChart(String localSelect);
    Map<String,Integer> useTotalLocalChart();

    UserPageVO getUserPage(int pageNumber);

    Map<String,Long> storeIndustryChartAll();
    Map<String,List<SalesVoucherUser>> salesList();
    Map<String, List<ReportListStore>> reportList();
    ReportListStore oneStore(Long id);
    Optional<ReportList> oneStroeReport(Long id);
    ReportList updateInitial(ReportList reportList);

    List<ReportListStore> storeSearch(String searchWord);
    Map<String, SalesVoucherUser> voucherSalesTotalChart();
    List<SalesVoucherUser> salesListSearch(String useStatus, String citySelect, String searchWord);
    

}

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ReportListRepository reportListRepository;


    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, ReportListRepository reportListRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.reportListRepository = reportListRepository;
    }


    @Override
    public Map<String,Long> localTotalChart() {
       return adminRepository.localTotalChart();
    }

    @Override
    public Map<String, Long> userLocalGenderChart(String localSelect) {
        return adminRepository.userLocalGenderChart(localSelect);
    }

    @Override
    public Map<String, Integer> userAgeTotal(String localSelect) {
        return adminRepository.userAgeChart(localSelect);
    }






    @Override
    public Map<String, Long> storeTypeChart() {
        return adminRepository.storeTypeLocal();
    }

    @Override
    public List<SalesVoucherUser> salesMonthChart() {
        return adminRepository.salesMonthChart();
    }

    @Override
    public List<User> getAllUserList() {
        return userRepository.findAll(); //페이지네이션
    }

    @Override
    public Optional<User> findOneUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Map<String,Integer> useLocalChart(String localName,LocalDate startDate,LocalDate endDate) {

        return adminRepository.useLocalChart(localName,startDate,endDate);
    }

    @Override
    public Map<String, SalesVoucherUser> voucherNameChart(String voucherName, String start, String end) {
        return adminRepository.voucherNameChart(voucherName,start,end);
    }

    @Override
    public Map<String, Integer> useTotalLocalChart() {
        return adminRepository.useTotalLocalChart();
    }

    @Override
    public UserPageVO getUserPage(int pageNumber) {
        UserPageVO result = new UserPageVO();
        List<RequestedUsersVO> users = new ArrayList<>();
        Page<User> pageUserList = userRepository.findAll(PageRequest.of(pageNumber, 20));
        pageUserList.forEach(user -> {
            RequestedUsersVO newUser = new RequestedUsersVO();
            newUser.setId(user.getId());
            newUser.setUserId(user.getUserId());
            newUser.setName(user.getName());
            newUser.setBirthDate(user.getBirthDate());
            newUser.setGender(user.getGender());
            newUser.setEmail(user.getEmail());
            newUser.setJoinDate(user.getJoinDate());
            newUser.setDefaultAddr(user.getDefaultAddr());
            newUser.setOptionalAddr(user.getOptionalAddr());
            users.add(newUser);
        });
        result.setTotalUsers(pageUserList.getTotalElements());
        result.setTotalPages(pageUserList.getTotalPages());
        result.setUsers(users);
        return result;
    }

    @Override
    public Map<String, Long> storeIndustryChartAll() {
        return adminRepository.storeTypeLocal();
    }

    @Override
    public Map<String, List<SalesVoucherUser>> salesList() {
        return adminRepository.salesList();
    }

    @Override
    public Map<String, List<ReportListStore>> reportList() {
        return adminRepository.reportList();
    }

    @Override
    public ReportListStore oneStore(Long id) {
        return adminRepository.getOneStore(id);
    }

    @Override
    public Optional<ReportList> oneStroeReport(Long id) {
        return reportListRepository.findByStoreId(id);
    }

    @Override
    public ReportList updateInitial(ReportList reportList) {
        return  reportListRepository.save(reportList);
    }

    @Override
    public List<ReportListStore> storeSearch(String searchWord) {
        return adminRepository.storeSearch(searchWord);
    }

    @Override
    public Map<String, SalesVoucherUser> voucherSalesTotalChart() {
        return adminRepository.voucherSalesTotalChart();
    }

    @Override
    public List<SalesVoucherUser> salesListSearch(String useStatus, String citySelect, String searchWord) {
        return adminRepository.salesListSearch(useStatus,citySelect,searchWord);
//=======
//    public UserPageVO getSearchedUsers(String selectedOption, String searchWord) {
//        UserPageVO result = new UserPageVO();
//        List<RequestedUsersVO> users = new ArrayList<>();
//        if(selectedOption.equals("userid")) {
//            Optional<User> searchedUser = userRepository.findByUserId(searchWord);
//            if(searchedUser.isPresent()) {
//                User user = searchedUser.get();
//                RequestedUsersVO newUser = new RequestedUsersVO();
//                newUser.setId(user.getId());
//                newUser.setUserId(user.getUserId());
//                newUser.setName(user.getName());
//                newUser.setBirthDate(user.getBirthDate());
//                newUser.setGender(user.getGender());
//                newUser.setEmail(user.getEmail());
//                newUser.setJoinDate(user.getJoinDate());
//                newUser.setDefaultAddr(user.getDefaultAddr());
//                newUser.setOptionalAddr(user.getOptionalAddr());
//                users.add(newUser);
//                result.setUsers(users);
//                result.setTotalPages(1);
//                result.setTotalUsers(0);
//                return result;
//            }
//        } else {
//            List<User> searchedUser = userRepository.findByUserName(searchWord);
//            searchedUser.forEach(user -> {
//                RequestedUsersVO newUser = new RequestedUsersVO();
//                newUser.setId(user.getId());
//                newUser.setUserId(user.getUserId());
//                newUser.setName(user.getName());
//                newUser.setBirthDate(user.getBirthDate());
//                newUser.setGender(user.getGender());
//                newUser.setEmail(user.getEmail());
//                newUser.setJoinDate(user.getJoinDate());
//                newUser.setDefaultAddr(user.getDefaultAddr());
//                newUser.setOptionalAddr(user.getOptionalAddr());
//                users.add(newUser);
//            });
//            result.setUsers(users);
//            result.setTotalUsers(searchedUser.size());
//            result.setTotalPages((int)Math.ceil(searchedUser.size()/20));
//            return result;
//        }
//
//        return null;
//>>>>>>> master
    }

    @Override
    public Map<String,Long> storeLocalsChart(String localSelect) {
        return adminRepository.storeLocalsChart(localSelect);
    }










}
