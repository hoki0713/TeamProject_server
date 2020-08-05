package com.mobeom.local_currency.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long>,CustomStoreRepository {
}
