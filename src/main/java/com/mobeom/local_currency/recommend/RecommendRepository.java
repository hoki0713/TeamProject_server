package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RecommendRepository extends JpaRepository<Recommend,Long>, CustomRecommendRepository{

}
