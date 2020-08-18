package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.favorites.Favorites;
import com.mobeom.local_currency.favorites.FavoritesRepository;
import com.mobeom.local_currency.sales.SalesRepository;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.store.StoreRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

class DummyServiceImplTest {
    @Test
    void createRandomFavorites() {
        FavoritesRepository favoritesRepository = Mockito.mock(FavoritesRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SalesRepository salesRepository = Mockito.mock(SalesRepository.class);
        LocalCurrencyVoucherRepository localCurrencyVoucherRepository = Mockito.mock(LocalCurrencyVoucherRepository.class);
        StoreRepository storeRepository = Mockito.mock(StoreRepository.class);
        RandomFavoritesGenerator randomFavoritesGenerator = Mockito.mock(RandomFavoritesGenerator.class);

        DummyServiceImpl dummyService = new DummyServiceImpl(
                userRepository,
                salesRepository,
                localCurrencyVoucherRepository,
                storeRepository,
                favoritesRepository, randomFavoritesGenerator, ratingRepository);

        User user = new User();
        user.setDefaultAddr("경기도 고양시 ");

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Mockito.when(randomFavoritesGenerator.hasFavorites()).thenReturn(true);

        Store store = new Store();
        store.setId(1L);

        Mockito.when(storeRepository.findAllStoreByUserDefaultAddr(Mockito.any())).thenReturn(Arrays.asList(store));

        Favorites favorites = new Favorites();
        favorites.setId(1L);

        Mockito.when(favoritesRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(favorites));

        dummyService.createRandomFavorites();
        Mockito.verify(userRepository).findAll();
        Mockito.verify(storeRepository).findAllStoreByUserDefaultAddr(Mockito.any());
        Mockito.verify(favoritesRepository).saveAll(Mockito.any());
    }
}