package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface RecommendRepository extends JpaRepository<GenderAge,Long>, CustomRecommendRepository{

//    @Query(value="SELECT store_id FROM favorites WHERE favorites.user_id = :id LIMIT 1", nativeQuery = true)
//    Long findStoreIdByUserId(@Param("id") String id);

}
