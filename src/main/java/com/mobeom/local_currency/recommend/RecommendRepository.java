package com.mobeom.local_currency.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecommendRepository extends JpaRepository<Recommend,Long>, CustomRecommendRepository{}
