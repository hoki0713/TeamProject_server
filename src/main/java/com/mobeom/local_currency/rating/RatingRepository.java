package com.mobeom.local_currency.rating;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RatingRepository extends JpaRepository<Rating, Long>, CustomRatingRepository {
}
