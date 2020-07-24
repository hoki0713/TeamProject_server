package com.mobeom.local_currency.voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface VoucherRepository extends JpaRepository<Voucher,Long> ,CustomedVoucherRepository {}

interface CustomedVoucherRepository {
    public List<Voucher> findAll();
}


public class VoucherRepositoryImpl extends QuerydslRepositorySupport implements CustomedVoucherRepository {

    public VoucherRepositoryImpl() { super(Voucher.class);}


    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
