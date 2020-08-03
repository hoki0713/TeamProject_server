package com.mobeom.local_currency.statistic;

import com.mobeom.local_currency.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<User, Long>, CustomStatisticRepository {

}
