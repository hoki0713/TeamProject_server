package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
interface DummyService {

    List<User> createRandomUser();
}

@Service
public class DummyServiceImpl implements DummyService{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> createRandomUser() {
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            User user = new User();
            user.setUserId(RandomUserGenerator.generateRandomId()+RandomUserGenerator.generateRandomNo2());
            user.setPassword(RandomUserGenerator.generateRandomPw()+RandomUserGenerator.generateRandomPwNum());
            user.setName(RandomUserGenerator.generateRandomName());
            user.setBirthDate(RandomUserGenerator.generateRandomBirthDate());
            user.setGender(RandomUserGenerator.generateRandomGender());
            user.setEmail(RandomUserGenerator.generateRandomEmailId()+"@"+RandomUserGenerator.generateRandomEmail()+
                    RandomUserGenerator.generateRandomEmailEnd());
            user.setJoinDate(RandomUserGenerator.generateRandomJoinDate());
            user.setAdminKey(0);
            user.setDefaultAddr(RandomUserGenerator.generateRandomAddress());
            userList.add(user);
        }
        return userRepository.saveAll(userList);
    }
}
