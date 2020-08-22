package com.mobeom.local_currency.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecommendRepository extends JpaRepository<Consume,Long>, CustomRecommendRepository{

//    @Query(value="SELECT store_id FROM favorites WHERE favorites.user_id = :id LIMIT 1", nativeQuery = true)
//    Long findStoreIdByUserId(@Param("id") String id);

}
