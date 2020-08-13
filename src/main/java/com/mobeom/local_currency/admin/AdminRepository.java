package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



public interface AdminRepository extends JpaRepository<Admin,Long>,CustomAdminRepository {

   @Query(value=" select * from sales sa where sa.sales_date like '%07%' ",nativeQuery=true)
    int currencySales();

}
