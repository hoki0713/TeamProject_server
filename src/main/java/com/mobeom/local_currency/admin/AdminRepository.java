package com.mobeom.local_currency.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public interface AdminRepository extends JpaRepository<Admin,Long>,CustomAdminRepository {

}
