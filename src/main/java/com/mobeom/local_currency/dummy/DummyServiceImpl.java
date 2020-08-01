package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.sales.SalesRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
interface DummyService {
    List<User> createRandomUser();

    List<Sales> createRandomPurchaseHistory();
}

@Service
public class DummyServiceImpl implements DummyService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    LocalCurrencyVoucherRepository localCurrencyVoucherRepository;

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
        List<LocalCurrencyVoucher> localCurrencyVoucherList = localCurrencyVoucherRepository.findAll();
        for(int i = 0; i < 10; i++) {
            Sales purchaseHistory = new Sales();
            int randomUserIndex = (int)(Math.random()*userList.size());
            int randomLocalCurrencyVoucherIndex = (int)(Math.random()*localCurrencyVoucherList.size());
            purchaseHistory.setSalesDate(RandomUserGenerator.generateRandomJoinDate());
            purchaseHistory.setCurrencyState(RandomPurchaseHistoryGenerator.generateRandomCurrencyState());
            purchaseHistory.setGiftYn(RandomPurchaseHistoryGenerator.generateRandomBoolean());
            if(purchaseHistory.getCurrencyState().equals("사용완료") || purchaseHistory.isGiftYn()) {
                purchaseHistory.setUseDate(RandomUserGenerator.generateRandomJoinDate());
            } else {
                purchaseHistory.setUseDate(null);
            }
            if(purchaseHistory.getCurrencyState().equals("취소완료")) {
                purchaseHistory.setCancelDate(RandomUserGenerator.generateRandomJoinDate());
            } else {
                purchaseHistory.setCancelDate(null);
            }
            purchaseHistory.setPaymentName(localCurrencyVoucherList.get(randomLocalCurrencyVoucherIndex).getLocalCurrencyName());
            purchaseHistory.setUnitPrice(RandomPurchaseHistoryGenerator.generateRandomVoucherPrice());
            purchaseHistory.setUser(userList.get(randomUserIndex));
            purchaseHistoryList.add(purchaseHistory);

        }
        return salesRepository.saveAll(purchaseHistoryList);
    }
}
