package com.mobeom.local_currency.favorites;

import com.mobeom.local_currency.store.Store;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

interface FavoritesService {

    Optional<Map<Long, FavoritesVO>> getFavoritesList(long userId);
}

@Service
public class FavoritesServiceImpl implements FavoritesService{
    private final FavoritesRepository favoritesRepository;

    public FavoritesServiceImpl(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public Optional<Map<Long, FavoritesVO>> getFavoritesList(long userId) {
        Map<Long, FavoritesVO> favorites = new HashMap<>();
        List<Favorites> userFavorites = favoritesRepository.findAllByUserId(userId);
        userFavorites.forEach(favorite -> {
            Store store = favorite.getStore();
            FavoritesVO oneFavoriteStore = new FavoritesVO();
            oneFavoriteStore.setStoreName(store.getStoreName());
            oneFavoriteStore.setStoreAddr(store.getAddress());
            oneFavoriteStore.setStorePhoneNumber(store.getStorePhone());
            oneFavoriteStore.setStoreStarRating(store.getStarRanking());
            favorites.put(store.getId(), oneFavoriteStore);
        });
        return Optional.of(favorites);
    }
}
