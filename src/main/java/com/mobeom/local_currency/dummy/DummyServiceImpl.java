package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.favorites.Favorites;
import com.mobeom.local_currency.favorites.FavoritesRepository;
import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.rating.RatingRepository;
import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.sales.SalesRepository;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.store.StoreRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
interface DummyService {
    List<User> createRandomUser();

    List<Sales> createRandomPurchaseHistory();

    List<Favorites> createRandomFavorites();

    List<Rating> createRandomRatingUijeongbu();
}

@Service
public class DummyServiceImpl implements DummyService{

    private final UserRepository userRepository;
    private final SalesRepository salesRepository;
    private final LocalCurrencyVoucherRepository localCurrencyVoucherRepository;
    private final StoreRepository storeRepository;
    private final FavoritesRepository favoritesRepository;
    private final RandomFavoritesGenerator randomFavoritesGenerator;
    private final RatingRepository ratingRepository;

    public DummyServiceImpl(UserRepository userRepository,
                            SalesRepository salesRepository,
                            LocalCurrencyVoucherRepository localCurrencyVoucherRepository,
                            StoreRepository storeRepository,
                            FavoritesRepository favoritesRepository,
                            RandomFavoritesGenerator randomFavoritesGenerator, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.salesRepository = salesRepository;
        this.localCurrencyVoucherRepository = localCurrencyVoucherRepository;
        this.storeRepository = storeRepository;
        this.favoritesRepository = favoritesRepository;
        this.randomFavoritesGenerator = randomFavoritesGenerator;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<User> createRandomUser() {
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            User user = new User();
            user.setUserId(RandomUserGenerator.generateRandomId()+RandomUserGenerator.generateRandomNo2());
            user.setPassword(RandomUserGenerator.generateRandomPw()+RandomUserGenerator.generateRandomPwNum());
            user.setName(RandomUserGenerator.generateRandomName());
            user.setBirthDate(RandomUserGenerator.generateRandomBirthDate());
            user.setGender(RandomUserGenerator.generateRandomGender());
            user.setEmail(RandomUserGenerator.generateRandomEmailId()+"@"+RandomUserGenerator.generateRandomEmail()+
                    RandomUserGenerator.generateRandomEmailEnd());
            user.setJoinDate(RandomUserGenerator.generateRandomJoinDate());
            user.setAdminKey(0);
            user.setDefaultAddr(RandomUserGenerator.generateRandomAddress());
            userList.add(user);
        }
        return userRepository.saveAll(userList);
    }

    @Override
    public List<Sales> createRandomPurchaseHistory() {
        List<Sales> purchaseHistoryList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(int i = 0; i < 100000; i++) {
            Sales purchaseHistory = new Sales();
            purchaseHistory.setUser(userList.get((int)(Math.random()*userList.size()-1)));
            purchaseHistory.setSalesDate(RandomPurchaseHistoryGenerator.generateRandomDate());
            purchaseHistory.setGiftYn(RandomPurchaseHistoryGenerator.generateRandomBoolean());

            if(purchaseHistory.isGiftYn()) {
                purchaseHistory.setCurrencyState("사용완료");
                purchaseHistory.setRecipientEmail(
                        RandomPurchaseHistoryGenerator.generateRandomEmailId()+"@"+
                        RandomPurchaseHistoryGenerator.generateRandomEmail()+
                        RandomPurchaseHistoryGenerator.generateRandomEmailEnd());
                purchaseHistory.setUseDate(purchaseHistory.getSalesDate().plusDays((int)(Math.random()*31)));
            } else {
                purchaseHistory.setCurrencyState(RandomPurchaseHistoryGenerator.generateRandomCurrencyState());
                if(purchaseHistory.getCurrencyState().equals("사용완료")) {
                    purchaseHistory.setUseDate(purchaseHistory.getSalesDate().plusDays((int)(Math.random()*31)));
                    purchaseHistory.setRecipientEmail(purchaseHistory.getUser().getEmail());
                } else {
                    purchaseHistory.setUseDate(null);
                }
                if(purchaseHistory.getCurrencyState().equals("취소완료")) {
                    purchaseHistory.setCancelDate(purchaseHistory.getSalesDate().plusDays((int)(Math.random()*7)));
                    purchaseHistory.setRecipientEmail(null);
                } else {
                    purchaseHistory.setCancelDate(null);
                }
                if(purchaseHistory.getCurrencyState().equals("미사용")) {
                    purchaseHistory.setRecipientEmail(null);
                }
            }

            purchaseHistory.setPaymentName(RandomPurchaseHistoryGenerator.generateRandomPaymentCompany());
            purchaseHistory.setUnitPrice(RandomPurchaseHistoryGenerator.generateRandomVoucherPrice());

            String address = purchaseHistory.getUser().getDefaultAddr();
            List<LocalCurrencyVoucher> selectedLocalCurrencyList =
                    localCurrencyVoucherRepository.findAllByDefaultAddr(address);
            purchaseHistory.setLocalCurrencyVoucher(
                    selectedLocalCurrencyList.get((int)(Math.random()*2)));
            purchaseHistoryList.add(purchaseHistory);
        }
        return salesRepository.saveAll(purchaseHistoryList);
    }

    @Override
    public List<Favorites> createRandomFavorites() {
        List<Favorites> favorites = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            String[] splitUserAddress = user.getDefaultAddr().split(" ");
            String targetAddress = splitUserAddress[0]+" "+splitUserAddress[1];
            if(targetAddress.equals("경기도 의정부시") || targetAddress.equals("경기도 고양시")) {
                if(randomFavoritesGenerator.hasFavorites()) {
                    int numOfFavorites = RandomFavoritesGenerator.getRandomNumOfFavorites();
                    List<Store> findAllStoreByUserDefaultAddr = storeRepository.findAllStoreByUserDefaultAddr(splitUserAddress[1]);
                    for(int i = 0; i < numOfFavorites; i++) {
                        Favorites favorite = new Favorites();
                        favorite.setUser(user);
                        favorite.setStore(findAllStoreByUserDefaultAddr.get((int) (Math.random() * findAllStoreByUserDefaultAddr.size())));
                        favorites.add(favorite);
                    }
                }
            }
        });
        return favoritesRepository.saveAll(favorites);
    }

    @Override
    public List<Rating> createRandomRatingUijeongbu() {
        List<User> allUsers = userRepository.findAll();
        List<Store> allStores = storeRepository.findAll();
        List<Store> uijeongbuStore = new ArrayList<>();
        List<User> uijeongbuUsers = new ArrayList<>();
        allUsers.forEach(user -> {
            if(user.getDefaultAddr().split(" ")[1].equals("의정부시")) uijeongbuUsers.add(user);
        });
        allStores.forEach(store -> {
            if(store.getLocalName().equals("의정부시")) uijeongbuStore.add(store);
        });

        List<Rating> resultRatings = new ArrayList<>();

        uijeongbuUsers.forEach(uijeongbuUser -> {
            for(int i = 0; i < (int)(Math.random()*100+20); i++) {
                Collections.shuffle(uijeongbuStore);
                Rating newRating = new Rating();
                newRating.setUser(uijeongbuUser);
                newRating.setStore(uijeongbuStore.get(0));
                resultRatings.add(newRating);
            }
        });
        return ratingRepository.saveAll(resultRatings);
    }
}
