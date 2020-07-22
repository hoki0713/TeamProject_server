package com.mobeom.local_currency.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

interface UserService {

    Optional<User> idCheck(String user);
}

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> idCheck(String user) {
        Optional<User> idCheck = userRepository.findByUserId(user);
        return idCheck;
    }
}
