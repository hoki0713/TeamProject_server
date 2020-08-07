package com.mobeom.local_currency.favorites;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Long>, CustomFavoritesRepository {

}
