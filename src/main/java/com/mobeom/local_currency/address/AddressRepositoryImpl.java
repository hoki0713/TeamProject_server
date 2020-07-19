package com.mobeom.local_currency.address;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface AddressRepository extends JpaRepository<Address, Long>, AddressService {}

interface AddressService {

}
public class AddressRepositoryImpl extends QuerydslRepositorySupport implements AddressService {
    @Autowired
    JPAQueryFactory query;

    AddressRepositoryImpl() {
        super(Address.class);
    }
}
