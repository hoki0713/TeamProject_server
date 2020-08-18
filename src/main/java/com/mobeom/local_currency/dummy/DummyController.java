package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.favorites.Favorites;
import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    @Autowired
    DummyService dummyService;

    @PostMapping("/user-generator")
    public ResponseEntity<List<User>> createRandomUser() {
        List<User> randomUserList = dummyService.createRandomUser();
        return ResponseEntity.ok(randomUserList);
    }

    @PostMapping("/purchase-history-generator")
    public ResponseEntity<List<Sales>> createRandomPurchaseHistory() {
        List<Sales> randomPurchaseHistoryList = dummyService.createRandomPurchaseHistory();
        return ResponseEntity.ok(randomPurchaseHistoryList);
    }

    @PostMapping("/favorites")
    public ResponseEntity<List<Favorites>> createRandomFavorites() {
        List<Favorites> randomFavoritesList = dummyService.createRandomFavorites();
        return ResponseEntity.ok(randomFavoritesList);
    }

    @PostMapping("/matching")
    public ResponseEntity<List<Rating>> createRandomRating() {
        List<Rating> randomRatingList = dummyService.createRandomRatingUijeongbu();
        return ResponseEntity.ok(randomRatingList);
    }
}
