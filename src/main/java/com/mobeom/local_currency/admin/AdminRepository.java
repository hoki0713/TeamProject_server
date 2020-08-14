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

    /*
    SELECT a.cancel_date,a.use_date,b.local_currency_name,a.sales_date,a.unit_price FROM sales a INNER Join local_currency_voucher b
 ON a.local_currency_voucher_id = b.local_currency_voucher_id where a.use_date
 BETWEEN 2020-07-01 AND 2020-07-31 IS NULL AND a.cancel_date  BETWEEN 2020-07-01 AND 2020-07-31 IS NULL
 AND b.local_currency_name LIKE '%고양%';
     */
//  @Query(value="select sum(a.unit_price) from sales a (inner join local_currency_voucher b on a.local_currency_voucher_id =: b.local_currency_voucher_id)" +
//          "where a.use_date between start and endDate is null and a.cancel_date between start and endDate is null and b.local_currency_name like %localName%")
//int testRepository(@Param("start")LocalDate start,@Param("endDate")LocalDate endDate,@Param("localName") String localName);
}
