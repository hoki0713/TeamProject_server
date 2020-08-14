package com.mobeom.local_currency.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<Store,Long>, IStoreRepository {



}

