package com.mobeom.local_currency.admin;


import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
interface AdminService{
    List<User> getAllList(String searchWord);
    Map<String,Long> chart();
    Map<String,Long> userTotal(String localSelect);
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
    public Map<String, Long> userTotal(String localSelect) {
        return adminRepository.userLocalChart(localSelect);
    }


}
