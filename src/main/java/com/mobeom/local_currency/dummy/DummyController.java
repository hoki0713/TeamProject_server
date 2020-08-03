package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

}
