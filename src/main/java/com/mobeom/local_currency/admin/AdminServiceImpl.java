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

@Component
interface AdminService{
    List<User> getAllList(String searchWord);
    Map<String,Long> chart();
    Map<String,Long> userLocalTotal(String localSelect);
    Map<String,Integer> userAgeTotal(String localSelect);
    Map<?,?> joinChart(LocalDate joinStartDate,LocalDate joinEndDate);
    Map<String,Long> storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeChart();


}

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getAllList(String searchWord) {
        return adminRepository.List(searchWord);
    }

    @Override
    public Map<String,Long> chart() {
       return adminRepository.chart();
    }

    @Override
    public Map<String, Long> userLocalTotal(String localSelect) {
        return adminRepository.userLocalChart(localSelect);
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
        return adminRepository.localsTotal(localSelect);
    }

    @Override
    public Map<String, Long> storeTypeChart() {
        return adminRepository.storeTypeLocal();
    }




}
