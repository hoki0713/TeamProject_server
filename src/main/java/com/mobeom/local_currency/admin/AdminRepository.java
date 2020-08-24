package com.mobeom.local_currency.admin;


import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Industry, Long>, CustomAdminRepository {

}
