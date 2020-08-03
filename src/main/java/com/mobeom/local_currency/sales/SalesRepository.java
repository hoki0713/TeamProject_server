package com.mobeom.local_currency.sales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long>, CustomSalesRepository{

}
