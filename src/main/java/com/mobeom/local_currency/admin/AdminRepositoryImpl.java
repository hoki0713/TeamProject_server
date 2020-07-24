package com.mobeom.local_currency.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ggbbAdminRepository extends JpaRepository<Admin,Long> ,CustomedAdminRepository {}

interface CustomedAdminRepository {
    public List<Admin> findAll();
}

public class AdminRepositoryImpl extends QuerydslRepositorySupport implements CustomedAdminRepository {

    public AdminRepositoryImpl() { super(Admin.class);}

    @Override
    public List<Admin> findAll() {
        return null;
    }
}
