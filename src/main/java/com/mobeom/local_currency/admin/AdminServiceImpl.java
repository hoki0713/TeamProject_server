package com.mobeom.local_currency.admin;


import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface AdminService{
    
    Map<String,Long> localTotalChart();
    Map<String,Long> userLocalGenderChart(String localSelect);
    Map<String,Integer> userAgeTotal(String localSelect);
    Map<?,?> joinChart(LocalDate joinStartDate,LocalDate joinEndDate);
    Map<String,Long> storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeChart();


    List<User> getAllUserList();

    Optional<User> findOneUser(String userId);
}

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;


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
    public Map<?, ?> joinChart(LocalDate joinStartDate,LocalDate joinEndDate) {
        return adminRepository.joinDateChart(joinStartDate,joinEndDate);
    }

    @Override
    public Map<String, Long> storeLocalsChart(String localSelect) {
        return adminRepository.storeLocalsChart(localSelect);
    }

    @Override
    public Map<String, Long> storeTypeChart() {
        return adminRepository.storeTypeLocal();
    }

    @Override
    public List<User> getAllUserList() {
        return userRepository.findAll(); //페이지네이션
    }

    @Override
    public Optional<User> findOneUser(String userId) {
        return userRepository.findByUserId(userId);
    }


}
