package com.mobeom.local_currency.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomedUserRepository {
    Optional<User> findByUserId(String userId);
}
