package com.mobeom.local_currency.favorites;

import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.store.StoreRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface FavoritesService {

    Optional<Map<Long, FavoritesVO>> getFavoritesList(long userId);

    Favorites saveFavorite(FavoritesVO favorite);
}

@Service
public class FavoritesServiceImpl implements FavoritesService{
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public FavoritesServiceImpl(FavoritesRepository favoritesRepository, UserRepository userRepository, StoreRepository storeRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
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
            favorites.put(store.getId(), oneFavoriteStore);
        });
        return Optional.of(favorites);
    }

    @Override
    public Favorites saveFavorite(FavoritesVO favorite) {
        Optional<User> findUser = userRepository.findById(favorite.getUserId());
        User user = findUser.get();
        Optional<Store> findStore = storeRepository.findById(favorite.getStoreId());
        Store store = findStore.get();
        Favorites newFavorite = new Favorites();
        newFavorite.setUser(user);
        newFavorite.setStore(store);
        return favoritesRepository.save(newFavorite);
    }
}
